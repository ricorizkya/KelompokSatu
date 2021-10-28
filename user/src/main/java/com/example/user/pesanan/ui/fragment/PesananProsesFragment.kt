package com.example.user.pesanan.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.user.R
import com.example.user.databinding.FragmentPesananProsesBinding
import com.example.user.pesanan.adapter.ProsesPesananAdapter
import com.example.user.pesanan.model.Pesanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PesananProsesFragment : Fragment() {

    private lateinit var binding: FragmentPesananProsesBinding
    private lateinit var prosesArrayList: ArrayList<Pesanan>
    private lateinit var prosesRecyclerView: RecyclerView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var query: Query
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPesananProsesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            getData()
        }
    }

    private fun getData() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email
        val emailStatus = "Belum Bayar - $email"
        query = FirebaseDatabase.getInstance().getReference("pesanan")
            .orderByChild("idUserStatusPembayaran").equalTo(emailStatus)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (pesananSnapshot in snapshot.children) {
                        val prosesPesanan = pesananSnapshot.getValue(Pesanan::class.java)
                        prosesArrayList.add(prosesPesanan!!)
                    }
                    prosesRecyclerView.adapter = ProsesPesananAdapter(prosesArrayList)

                    prosesRecyclerView = binding.rvProses
                    prosesRecyclerView.layoutManager = LinearLayoutManager(context)
                    prosesRecyclerView.setHasFixedSize(true)
                    prosesArrayList = arrayListOf()

                    lottieAnimationView.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                lottieAnimationView = binding.imgEmpty
                lottieAnimationView.animate()
                lottieAnimationView.visibility = View.VISIBLE
            }
        })
    }
}