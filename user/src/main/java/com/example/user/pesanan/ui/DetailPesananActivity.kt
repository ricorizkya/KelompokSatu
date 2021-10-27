package com.example.user.pesanan.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.user.R
import com.example.user.databinding.ActivityDetailPesananBinding
import com.example.user.pesanan.model.Pesanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailPesananActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailPesananBinding
    private lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    private fun getData() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email.toString()
        val idUSerStatus = "Terbayar - $email"

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                query = FirebaseDatabase.getInstance().getReference("pesanan")
                    .orderByChild("idPesanan")
                    .equalTo(id)
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (pesananSnapshot in snapshot.children) {
                            val pesanan = pesananSnapshot.getValue(Pesanan::class.java)
                            binding.tvNamaDepanUser.text = pesanan?.namaDepan
                            binding.tvNamaBelakangUser.text = pesanan?.namaBelakang
                            binding.tvNomorUser.text = pesanan?.nomorTelepon
                            binding.tvEmailUser.text = pesanan?.emailUser
                            binding.tvAlamatUser.text = pesanan?.alamatUser
                            binding.tvNamaPaket.text = pesanan?.namaPaket
                            binding.tvAlamatPaket.text = pesanan?.alamatPaket
                            binding.tvDurasiPaket.text = pesanan?.durasiPaket
                            binding.tvTanggalBerangkat.text = pesanan?.tglBerangkat
                            binding.tvGuide.text = pesanan?.guide
                            binding.tvTotalPaket.text = pesanan?.hargaPaket
                            Glide.with(applicationContext)
                                .load(pesanan?.fotoPembayaran)
                                .into(binding.imgTransfer)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }
}