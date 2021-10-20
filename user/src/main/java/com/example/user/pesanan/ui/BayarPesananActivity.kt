package com.example.user.pesanan.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.user.R
import com.example.user.databinding.ActivityBayarPesananBinding
import com.example.user.pesanan.model.Pesanan
import com.example.user.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class BayarPesananActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityBayarPesananBinding
    private lateinit var query: Query
    var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBayarPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressCircular.visibility = View.INVISIBLE
        getData()

        binding.btnImage.setOnClickListener {
            Log.d("Upload Gambar", "Pilih Gambar")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityImage.launch(intent)
            binding.imgPoster.visibility = View.INVISIBLE
        }

        binding.btnVerif.setOnClickListener {
            simpanUpdatePesanan()
            binding.progressCircular.visibility = View.VISIBLE
        }
    }

    var activityImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
            val image = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmap = BitmapDrawable(image)
            binding.imgPoster.visibility = View.INVISIBLE
            binding.btnImage.setBackgroundDrawable(bitmap)
            binding.btnVerif.isEnabled = true
        }
    }

    private fun getData() {
        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
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
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun updatePesanan(statusPembayaran: String, idUserStatusPembayaran: String, image: String) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.email.toString()
        val status = "Terbayar"
        val idUserStatus = "$status - $currentUser"
        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("pesanan")
                val pesanan = mapOf(
                        "statusPembayaran" to status,
                        "idUserStatusPembayaran" to idUserStatus,
                        "fotoPembayaran" to image
                )
                ref.child(id).updateChildren(pesanan).addOnSuccessListener {
                    Log.d("UpdateData", "Update Data Berhasil")
                    Toast.makeText(applicationContext, "Pembayaran Berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Log.d("UpdateData", "Update Data Gagal")
                }
            }
        }
    }

    private fun simpanUpdatePesanan() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.email.toString()
        val status = "Terbayar"
        val idUserStatus = "$status - $currentUser"

        if (selectedImageUri == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/user/pembayaran/$fileName")
        ref.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    Log.d("UploadGambar", "Berhasil Upload Gambar: ${it.metadata?.path}")
                    ref.downloadUrl.addOnSuccessListener {
                        Log.d("UploadGambar", "File Location: $it")
                        updatePesanan(status, idUserStatus, it.toString())
                    }
                }
                .removeOnFailureListener {
                    Log.d("UploadGambar", "Gagal Upload Gambar")
                }
    }
}