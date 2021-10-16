package com.example.user.paket.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.R
import com.example.user.databinding.FragmentPaketBinding
import com.example.user.paket.adapter.PaketAdapter
import com.example.user.paket.model.Paket
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class PaketFragment : Fragment() {

    private lateinit var binding: FragmentPaketBinding
    private lateinit var paketRecyclerView: RecyclerView
    private lateinit var paketArrayList: ArrayList<Paket>
    private lateinit var tempPaketArrayList: ArrayList<Paket>
    private lateinit var query: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPaketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            paketRecyclerView = binding.rvSearch
            paketRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            paketRecyclerView.setHasFixedSize(true)
            paketArrayList = arrayListOf()
            tempPaketArrayList = arrayListOf()
            getData()

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val searchT = newText!!.toLowerCase(Locale.getDefault())
                    if (searchT.isNotEmpty()) {
                        paketArrayList.forEach {
                            if (it.namaPaket.toString().toLowerCase(Locale.getDefault()).contains(searchT)) {
                                tempPaketArrayList.add(it)
                            }
                        }
                        paketRecyclerView.adapter!!.notifyDataSetChanged()
                    }else {
                        paketArrayList.clear()
                        tempPaketArrayList.addAll(paketArrayList)
                        paketRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return false
                }
            })
        }
    }

    private fun getData() {
        query = FirebaseDatabase.getInstance().getReference("paket")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketArrayList.add(paket!!)
                    }
                    tempPaketArrayList.addAll(paketArrayList)
                    paketRecyclerView.adapter = PaketAdapter(tempPaketArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}