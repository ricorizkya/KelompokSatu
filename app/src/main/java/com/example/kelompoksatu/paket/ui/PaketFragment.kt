package com.example.kelompoksatu.paket.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.FragmentPaketBinding
import com.example.kelompoksatu.paket.adapter.PaketAdapter
import com.example.kelompoksatu.paket.model.Paket
import com.google.firebase.database.*

class PaketFragment : Fragment() {

    private lateinit var binding: FragmentPaketBinding
    private lateinit var paketRecyclerView: RecyclerView
    private lateinit var paketArrayList: ArrayList<Paket>
    private lateinit var query: Query

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            binding.btnAddPackage.setOnClickListener {
                activity?.startActivity(Intent(activity, TambahPaketActivity::class.java))
            }

            paketRecyclerView = binding.rvPaket
            paketRecyclerView.layoutManager = LinearLayoutManager(context)
            paketRecyclerView.setHasFixedSize(true)

            paketArrayList = arrayListOf()
            getPaketData()
        }
    }

    fun getPaketData() {
        query = FirebaseDatabase.getInstance().getReference("paket")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketArrayList.add(paket!!)
                    }
                    paketRecyclerView.adapter = PaketAdapter(paketArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}