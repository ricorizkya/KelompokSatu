package com.example.user.paket.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.user.R
import com.example.user.databinding.ActivityDetailPaketBinding
import com.example.user.paket.model.Paket
import com.example.user.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailPaketActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_EMAIL = "extra_email"
    }

    private lateinit var binding: ActivityDetailPaketBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email.toString()

        val title = intent.getStringExtra(EXTRA_TITLE)
        supportActionBar?.title = title

        getDataPaket()
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

                            if (paket?.status == "Tidak Wishlist") {
                                binding.btnWishlist.visibility = View.VISIBLE
                                binding.btnNotWishlist.setOnClickListener {
                                    addWishlist()
                                    binding.btnNotWishlist.setImageResource(R.drawable.ic_baseline_favorite_24)
                                }
                            }else if (paket?.status == "Wishlist") {
                                binding.btnNotWishlist.visibility = View.VISIBLE
                                binding.btnWishlist.setOnClickListener {
                                    deleteWishlist()
                                    binding.btnWishlist.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                                }
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun addWishlist() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email.toString()
        val status = "Wishlist"
        val emailStatus = "$email - $status"

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("paket")
                val paket = mapOf(
                        "status" to status,
                        "emailUserStatus" to emailStatus
                )
                ref.child(id).updateChildren(paket).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Paket Berhasil Ditambahkan ke Wishlist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteWishlist() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email.toString()
        val status = "Tidak Wishlist"
        val emailStatus = "$email - $status"

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("paket")
                val paket = mapOf(
                        "status" to status,
                        "emailUserStatus" to emailStatus
                )
                ref.child(id).updateChildren(paket).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Paket Berhasil Dihapus Wishlist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}