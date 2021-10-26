package com.example.user.dashboard.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.databinding.FragmentDashboardBinding
import com.example.user.dashboard.adapter.HotPaketAdapter
import com.example.user.dashboard.adapter.GuideAdapter
import com.example.user.dashboard.model.Guide
import com.example.user.paket.model.Paket
import com.example.user.paket.ui.domisili.*
import com.google.firebase.database.*

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var paketRecyclerView: RecyclerView
    private lateinit var guideRecyclerView: RecyclerView
    private lateinit var paketArrayList: ArrayList<Paket>
    private lateinit var guideArrayList: ArrayList<Guide>
    private lateinit var query: Query

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()

        if (activity != null) {
            paketRecyclerView = binding.rvHotPaket
            paketRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            paketRecyclerView.setHasFixedSize(true)
            paketArrayList = arrayListOf()
            getDataPaket()

            guideRecyclerView = binding.rvGuide
            guideRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            guideRecyclerView.setHasFixedSize(true)
            guideArrayList = arrayListOf()
            getDataGuide()

            //Domisili
            binding.cvJatim.setOnClickListener {
                startActivity(Intent(activity, JatimActivity::class.java))
            }

            binding.cvJateng.setOnClickListener {
                startActivity(Intent(activity, JatengActivity::class.java))
            }

            binding.cvJabar.setOnClickListener {
                startActivity(Intent(activity, JabarActivity::class.java))
            }

            binding.cvJambi.setOnClickListener {
                startActivity(Intent(activity, JambiActivity::class.java))
            }

            binding.cvBali.setOnClickListener {
                startActivity(Intent(activity, BaliActivity::class.java))
            }

            binding.cvSumbar.setOnClickListener {
                startActivity(Intent(activity, SumbarActivity::class.java))
            }

        }
    }

    private fun getDataPaket() {
        query = FirebaseDatabase.getInstance().getReference("paket")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketArrayList.add(paket!!)
                    }
                    paketRecyclerView.adapter = HotPaketAdapter(paketArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getDataGuide() {
        query = FirebaseDatabase.getInstance().getReference("guide")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (guideSnapshot in snapshot.children) {
                        val guide = guideSnapshot.getValue(Guide::class.java)
                        guideArrayList.add(guide!!)
                    }
                    guideRecyclerView.adapter = GuideAdapter(guideArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}