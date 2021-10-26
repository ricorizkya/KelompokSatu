package com.example.kelompoksatu.pesanan.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.ActivityDetailPesananBinding
import com.example.kelompoksatu.pesanan.model.Pesanan
import com.example.kelompoksatu.ui.MainActivity
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

        binding.progressCircular.visibility = View.INVISIBLE
        getData()

        binding.btnAcc.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            accPesanan()
        }

        binding.btnReject.setOnClickListener {
            rejectPesanan()
            emailReject()
        }
    }

    private fun getData() {
        val extras = intent
        val id = extras.getStringExtra(EXTRA_ID)

        if (extras != null) {
            query = FirebaseDatabase.getInstance().getReference("pesanan")
                    .orderByChild("idPesanan")
                    .equalTo(id)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (pesananSnapshot in snapshot.children) {
                        val pesanan = pesananSnapshot.getValue(Pesanan::class.java)
                        if (pesanan?.statusPembayaran.toString() == "Belum Bayar") {
                            binding.tvTerbayar.visibility = View.GONE
                        }else if (pesanan?.statusPembayaran.toString() == "Terbayar") {
                            binding.tvBelumBayar.visibility = View.GONE
                            binding.tvInputGuide.visibility = View.GONE
                            binding.inputNamaGuide.visibility = View.GONE
                            binding.edtNamaGuide.visibility = View.GONE
                            binding.btnAcc.visibility = View.GONE
                            binding.btnReject.visibility = View.GONE
                        }

                        //Data User
                        binding.tvIdPesanan.text = pesanan?.idPesanan
                        binding.tvNamaDepanUser.text = pesanan?.namaDepan
                        binding.tvNamaBelakangUser.text = pesanan?.namaBelakang
                        binding.tvNomorUser.text = pesanan?.nomorTelepon
                        binding.tvEmailUser.text = pesanan?.emailUser
                        binding.tvAlamatUser.text = pesanan?.alamatUser
                        Glide.with(applicationContext)
                                .load(pesanan?.fotoKTP)
                                .into(binding.imgKtpUser)
                        Glide.with(applicationContext)
                                .load(pesanan?.fotoPembayaran)
                                .into(binding.imgBuktiUser)

                        //Data Paket
                        binding.tvNamaPaket.text = pesanan?.namaPaket
                        binding.tvAlamatPaket.text = pesanan?.alamatPaket
                        binding.tvDurasiPaket.text = pesanan?.durasiPaket
                        binding.tvHargaPaket.text = pesanan?.hargaPaket
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun accPesanan() {
        if (binding.edtNamaGuide.text.toString().isEmpty()) {
            binding.edtNamaGuide.error = "Input Nama Guide"
            return Toast.makeText(applicationContext, "Input Nama Guide", Toast.LENGTH_SHORT).show()
        }

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("pesanan")

                //Get Data
                query = FirebaseDatabase.getInstance().getReference("pesanan")
                    .orderByChild("idPesanan")
                    .equalTo(id)
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (pesananSnapshot in snapshot.children) {
                            val pesanan = pesananSnapshot.getValue(Pesanan::class.java)
                            val receipent = pesanan?.emailUser
                            val subject = pesanan?.namaPaket
                            val message = pesanan?.tglBerangkat

                            val intent = Intent(Intent.ACTION_SEND)
                            intent.data = Uri.parse("mailto:")
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_EMAIL, receipent)
                            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                            intent.putExtra(Intent.EXTRA_TEXT, "Pesanan Anda Berhasil Terverifikasi. Sampai bertemu di tanggal $message !")

                            try {
                                startActivity(Intent.createChooser(intent, "Pilih aplikasi untuk kirim email..."))
                            }catch (e: Exception) {
                                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

                //Update Data
                val pesanan = mapOf(
                    "guide" to binding.edtNamaGuide.text.toString(),
//                    "statusPembayaran" to "Terbayar",
                    "statusPesanan" to "Accept",
                    "keterangan" to binding.edtKeterangan.text.toString()
                )
                ref.child(id).updateChildren(pesanan).addOnSuccessListener {
                    Log.d("ACCPESANAN","ACC Pesanan")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun rejectPesanan() {
        if (binding.edtKeterangan.text.toString().isEmpty()) {
            binding.edtKeterangan.error = "Tulis Keterangan"
            return Toast.makeText(applicationContext, "Tulis Keterangan", Toast.LENGTH_SHORT).show()
        }

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("pesanan")
                val pesanan = mapOf(
                    "guide" to "-",
                    "statusPembayaran" to "Terbayar",
                    "statusPesanan" to "Reject",
                    "keterangan" to binding.edtKeterangan.text.toString()
                )
                ref.child(id).updateChildren(pesanan).addOnSuccessListener {
                    Log.d("REJECTPESANAN", "REJECT Pesanan")
                }
            }
        }
    }

    private fun emailAcc(email: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, arrayOf(subject))
        intent.putExtra(Intent.EXTRA_TEXT, arrayOf(message))

        try {
            startActivity(Intent.createChooser(intent, "Pilih aplikasi untuk kirim email..."))
        }catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun emailReject() {

    }
}