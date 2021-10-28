package com.example.user.paket.ui.domisili

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.databinding.ActivityBaliBinding
import com.example.user.paket.adapter.PaketAdapter
import com.example.user.paket.model.Paket
import com.google.firebase.database.*

class BaliActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaliBinding
    private lateinit var paketRecyclerView: RecyclerView
    private lateinit var paketArrayList: ArrayList<Paket>
    private lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        paketRecyclerView = binding.rvBali
        paketRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        paketRecyclerView.setHasFixedSize(true)
        paketArrayList = arrayListOf()
        getDataPaket()
    }

    private fun getDataPaket() {
        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("domisili")
                .equalTo("Bali")
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