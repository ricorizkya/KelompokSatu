package com.example.kelompoksatu.paket.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.ActivityEditPaketBinding
import com.example.kelompoksatu.paket.model.Paket
import com.example.kelompoksatu.ui.MainActivity
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class EditPaketActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityEditPaketBinding
    private lateinit var query: Query
    var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        binding.progressCircular.visibility = View.INVISIBLE

        getDataPaket()
        binding.imgPoster.visibility = View.INVISIBLE

        binding.btnImage.setOnClickListener {
            Log.d("Upload Gambar", "Pilih Gambar")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityImage.launch(intent)
            binding.imgPoster.visibility = View.INVISIBLE
        }

        binding.btnEdit.setOnClickListener {
            simpanUpdatePaket()
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
        }
    }

    private fun getDataPaket() {
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
                        binding.edtNamaPaket.setText(paket?.namaPaket)
                        binding.edtDomisiliPaket.setText(paket?.domisili)
                        binding.edtDurasiPaket.setText(paket?.durasi)
                        binding.edtHargaPaket.setText(paket?.harga)
                        binding.edtAlamatPaket.setText(paket?.alamatlengkap)
                        binding.edtDeskripsiPaket.setText(paket?.deskripsi)
                        binding.imgPoster.visibility = View.GONE
                        Glide.with(applicationContext)
                            .load(paket?.imagePoster)
                            .into(binding.btnImage)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun updatePaketData(nama: String, domisili: String, alamat: String, durasi: String, deskripsi: String, harga: String, image: String) {
        if (binding.edtNamaPaket.text.toString().isEmpty() && binding.edtDomisiliPaket.text.toString().isEmpty() && binding.edtDurasiPaket.text.toString().isEmpty() && binding.edtHargaPaket.text.toString().isEmpty() && binding.edtAlamatPaket.text.toString().isEmpty() && binding.edtDeskripsiPaket.text.toString().isEmpty() && image.isEmpty()) {
            binding.edtNamaPaket.error = "Form tidak boleh kosong"
            binding.edtDomisiliPaket.error = "Form tidak boleh kosong"
            binding.edtDurasiPaket.error = "Form tidak boleh kosong"
            binding.edtHargaPaket.error = "Form tidak boleh kosong"
            binding.edtAlamatPaket.error = "Form tidak boleh kosong"
            binding.edtDeskripsiPaket.error = "Form tidak boleh kosong"
            return Toast.makeText(applicationContext, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("paket")
                val paket = mapOf<String, String>(
                    "namaPaket" to nama,
                    "domisili" to domisili,
                    "alamatlengkap" to alamat,
                    "durasi" to durasi,
                    "deskripsi" to deskripsi,
                    "harga" to harga,
                    "imagePoster" to image
                )
                ref.child(id).updateChildren(paket).addOnSuccessListener {
                    Log.d("UpdateData", "Update Data Berhasil")
                    Toast.makeText(applicationContext, "Data Berhasil di Update", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Log.d("UpdateData", "Update Data Gagal")
                }
            }
        }
    }

    private fun simpanUpdatePaket() {
        val nama = binding.edtNamaPaket.text.toString()
        val domisili = binding.edtDomisiliPaket.text.toString()
        val alamat = binding.edtAlamatPaket.text.toString()
        val durasi = binding.edtDurasiPaket.text.toString()
        val deskripsi = binding.edtDeskripsiPaket.text.toString()
        val harga = binding.edtHargaPaket.text.toString()

        if (selectedImageUri == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/paket/$fileName")
        ref.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                Log.d("UploadGambar", "Berhasil Upload Gambar: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("UploadGambar", "File Location: $it")
                    updatePaketData(nama, domisili, alamat, durasi, deskripsi, harga, it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("Upload Gambar", "Gagal Upload Gambar")
            }
    }
}