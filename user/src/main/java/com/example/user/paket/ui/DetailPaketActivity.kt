package com.example.user.paket.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.user.R
import com.example.user.databinding.ActivityDetailPaketBinding
import com.example.user.paket.model.Paket
import com.example.user.ui.MainActivity
import com.google.firebase.database.*

class DetailPaketActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
    }

    private lateinit var binding: ActivityDetailPaketBinding
    private lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        val title = intent.getStringExtra(EXTRA_TITLE)
        supportActionBar?.title = title

        getDataPaket()

        binding.btnPesan.setOnClickListener {
            val intent = Intent(this, PesanPaketActivity::class.java)
            intent.putExtra(PesanPaketActivity.EXTRA_ID, id)
            startActivity(intent)
        }
    }

    private fun getDataPaket() {
        val extras = intent
        val id = intent.getStringExtra(EXTRA_ID)
        if (extras != null) {
            query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("id")
                .equalTo(id)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (paketSnapshot in snapshot.children) {
                            val paket = paketSnapshot.getValue(Paket::class.java)
                            binding.tvNamaPaket.text = paket?.namaPaket
                            binding.tvAlamat.text = paket?.alamatlengkap
                            binding.tvDomisili.text = paket?.domisili
                            binding.tvDurasi.text = paket?.durasi
                            binding.tvDesc.text = paket?.deskripsi
                            Glide.with(applicationContext)
                                .load(paket?.imagePoster)
                                .into(binding.imgPoster)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}