package com.example.user.paket.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.R
import com.example.user.R.layout.list_paket
import com.example.user.databinding.FragmentPaketBinding
import com.example.user.databinding.ListPaketBinding
import com.example.user.paket.adapter.PaketAdapter
import com.example.user.paket.model.Paket
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class PaketFragment : Fragment() {

    private lateinit var binding: FragmentPaketBinding
    private lateinit var paketJatimRecyclerView: RecyclerView
    private lateinit var paketJatimArrayList: ArrayList<Paket>
    private lateinit var paketJatengRecyclerView: RecyclerView
    private lateinit var paketJatengArrayList: ArrayList<Paket>
    private lateinit var paketJabarRecyclerView: RecyclerView
    private lateinit var paketJabarArrayList: ArrayList<Paket>
    private lateinit var paketJambiRecyclerView: RecyclerView
    private lateinit var paketJambiArrayList: ArrayList<Paket>
    private lateinit var paketBaliRecyclerView: RecyclerView
    private lateinit var paketBaliArrayList: ArrayList<Paket>
    private lateinit var paketSumbarRecyclerView: RecyclerView
    private lateinit var paketSumbarArrayList: ArrayList<Paket>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var query: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPaketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            getPaketJatim()
            getPaketJateng()
            getPaketJabar()
            getPaketJambi()
            getPaketBali()
            getPaketSumbar()
        }
    }

    fun getPaketJatim() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Jawa Timur")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketJatimArrayList.add(paket!!)
                    }
                    paketJatimRecyclerView.adapter = PaketAdapter(paketJatimArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        paketJatimRecyclerView = binding.rvJatim
        paketJatimRecyclerView.layoutManager = LinearLayoutManager(context)
        paketJatimRecyclerView.setHasFixedSize(true)
        paketJatimArrayList = arrayListOf()
    }

    fun getPaketJateng() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Jawa Tengah")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketJatengArrayList.add(paket!!)
                    }
                    paketJatengRecyclerView.adapter = PaketAdapter(paketJatengArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        paketJatengRecyclerView = binding.rvJateng
        paketJatengRecyclerView.layoutManager = LinearLayoutManager(context)
        paketJatengRecyclerView.setHasFixedSize(true)
        paketJatengArrayList = arrayListOf()
    }

    fun getPaketJabar() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Jawa Barat")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketJabarArrayList.add(paket!!)
                    }
                    paketJabarRecyclerView.adapter = PaketAdapter(paketJabarArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        paketJabarRecyclerView = binding.rvJabar
        paketJabarRecyclerView.layoutManager = LinearLayoutManager(context)
        paketJabarRecyclerView.setHasFixedSize(true)
        paketJabarArrayList = arrayListOf()
    }

    fun getPaketJambi() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Jambi")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketJambiArrayList.add(paket!!)
                    }
                    paketJambiRecyclerView.adapter = PaketAdapter(paketJambiArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        paketJambiRecyclerView = binding.rvJambi
        paketJambiRecyclerView.layoutManager = LinearLayoutManager(context)
        paketJambiRecyclerView.setHasFixedSize(true)
        paketJambiArrayList = arrayListOf()
    }

    fun getPaketBali() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Bali")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketBaliArrayList.add(paket!!)
                    }
                    paketBaliRecyclerView.adapter = PaketAdapter(paketBaliArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        paketBaliRecyclerView = binding.rvBali
        paketBaliRecyclerView.layoutManager = LinearLayoutManager(context)
        paketBaliRecyclerView.setHasFixedSize(true)
        paketBaliArrayList = arrayListOf()
    }

    fun getPaketSumbar() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Sumatra Barat")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        paketSumbarArrayList.add(paket!!)
                    }
                    paketSumbarRecyclerView.adapter = PaketAdapter(paketSumbarArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        paketSumbarRecyclerView = binding.rvSumbar
        paketSumbarRecyclerView.layoutManager = LinearLayoutManager(context)
        paketSumbarRecyclerView.setHasFixedSize(true)
        paketSumbarArrayList = arrayListOf()
    }

}