package com.example.kelompoksatu.pesanan.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.FragmentProsesPesananBinding
import com.example.kelompoksatu.pesanan.adapter.ProsesPesananAdapter
import com.example.kelompoksatu.pesanan.model.Pesanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProsesPesananFragment : Fragment() {

    private lateinit var binding: FragmentProsesPesananBinding
    private lateinit var prosesArrayList: ArrayList<Pesanan>
    private lateinit var prosesRecyclerView: RecyclerView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var query: Query

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProsesPesananBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            prosesRecyclerView = binding.rvPesanan
            prosesRecyclerView.layoutManager = LinearLayoutManager(context)
            prosesRecyclerView.setHasFixedSize(true)
            prosesArrayList = arrayListOf()

            getData()
        }
    }

    private fun getData() {
        query = FirebaseDatabase.getInstance().getReference("pesanan")
                .orderByChild("statusPembayaran").equalTo("Belum Bayar")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (pesananSnapshot in snapshot.children) {
                        val prosesPesanan = pesananSnapshot.getValue(Pesanan::class.java)
                        prosesArrayList.add(prosesPesanan!!)
                    }
                    binding.imgEmpty.visibility = View.INVISIBLE
                    prosesRecyclerView.adapter = ProsesPesananAdapter(prosesArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                lottieAnimationView = binding.imgEmpty
                lottieAnimationView.animate()
                binding.imgEmpty.visibility = View.VISIBLE
            }
        })
    }

}