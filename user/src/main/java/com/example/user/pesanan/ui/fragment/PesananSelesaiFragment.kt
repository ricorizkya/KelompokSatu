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
import com.example.user.databinding.FragmentPesananSelesaiBinding
import com.example.user.pesanan.adapter.ProsesPesananAdapter
import com.example.user.pesanan.adapter.SelesaiPesananAdapter
import com.example.user.pesanan.model.Pesanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PesananSelesaiFragment : Fragment() {

    private lateinit var binding: FragmentPesananSelesaiBinding
    private lateinit var selesaiArrayList: ArrayList<Pesanan>
    private lateinit var selesaiRecyclerView: RecyclerView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var query: Query
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPesananSelesaiBinding.inflate(layoutInflater, container, false)
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
        val emailStatus = "Terbayar - $email"
        query = FirebaseDatabase.getInstance().getReference("pesanan")
                .orderByChild("idUserStatusPembayaran").equalTo(emailStatus)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (pesananSnapshot in snapshot.children) {
                        val prosesPesanan = pesananSnapshot.getValue(Pesanan::class.java)
                        selesaiArrayList.add(prosesPesanan!!)
                    }
                    selesaiRecyclerView.adapter = SelesaiPesananAdapter(selesaiArrayList)

                    selesaiRecyclerView = binding.rvSelesai
                    selesaiRecyclerView.layoutManager = LinearLayoutManager(context)
                    selesaiRecyclerView.setHasFixedSize(true)
                    selesaiArrayList = arrayListOf()

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