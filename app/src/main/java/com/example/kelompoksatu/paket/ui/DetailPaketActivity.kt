package com.example.kelompoksatu.paket.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kelompoksatu.databinding.ActivityDetailPaketBinding
import com.example.kelompoksatu.paket.model.Paket
import com.example.kelompoksatu.ui.MainActivity
import com.example.kelompoksatu.paket.ui.EditPaketActivity
import com.google.firebase.database.*

class DetailPaketActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailPaketBinding
    private lateinit var query: Query
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        getDetailPaket()

        binding.btnUpdate.setOnClickListener {
            val sendId = Intent(this, EditPaketActivity::class.java)
            sendId.putExtra(EditPaketActivity.EXTRA_ID, id)
            startActivity(sendId)
        }

        binding.btnDelete.setOnClickListener {
            if (id != null) {
                deleteData(id)
            }
        }
    }

    private fun getDetailPaket() {
        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            query = FirebaseDatabase.getInstance().getReference("paket")
                    .orderByChild("id")
                    .equalTo(id)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (paketSnapshot in snapshot.children) {
                        val paket = paketSnapshot.getValue(Paket::class.java)
                        binding.tvNamaPaket.text = paket?.namaPaket
                        binding.tvDomisili.text = paket?.domisili
                        binding.tvAlamat.text = paket?.alamatlengkap
                        binding.tvDurasi.text = paket?.durasi
                        binding.tvDeskripsi.text = paket?.deskripsi
                        binding.tvHarga.text = paket?.harga
                        Glide.with(applicationContext)
                                .load(paket?.imagePoster)
                                .into(binding.imgPoster)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun deleteData(id: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("paket")
        databaseReference.child(id).removeValue().addOnSuccessListener {
            Toast.makeText(applicationContext, "Data Paket Dihapus", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}