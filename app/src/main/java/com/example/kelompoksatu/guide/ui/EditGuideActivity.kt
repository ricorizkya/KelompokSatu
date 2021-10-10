package com.example.kelompoksatu.guide.ui

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
import com.bumptech.glide.Glide
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.ActivityEditGuideBinding
import com.example.kelompoksatu.guide.model.Guide
import com.example.kelompoksatu.ui.MainActivity
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class EditGuideActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityEditGuideBinding
    private lateinit var query: Query
    var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressCircular.visibility = View.INVISIBLE
        getDataGuide()

        binding.btnImageProfile.setOnClickListener {
            Log.d("Upload Gambar", "Pilih Gambar")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityImage.launch(intent)
            binding.imgPosterProfile.visibility = View.INVISIBLE
        }

        binding.btnEdit.setOnClickListener {
            simpanUpdateGuide()
            binding.progressCircular.visibility = View.VISIBLE
        }
    }

    var activityImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
            val image = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmap = BitmapDrawable(image)
            binding.imgPosterProfile.visibility = View.INVISIBLE
            binding.btnImageProfile.setBackgroundDrawable(bitmap)
        }
    }

    private fun getDataGuide() {
        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            query = FirebaseDatabase.getInstance().getReference("guide")
                .orderByChild("id")
                .equalTo(id)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (guideSnapshot in snapshot.children) {
                        val guide = guideSnapshot.getValue(Guide::class.java)
                        binding.edtNamaGuide.setText(guide?.namaGuide)
                        binding.edtNomorGuide.setText(guide?.nomorGuide)
                        binding.edtEmailGuide.setText(guide?.emailGuide)
                        binding.edtAlamatGuide.setText(guide?.alamatGuide)
                        Glide.with(applicationContext)
                            .load(guide?.fotoGuide)
                            .into(binding.imgPosterProfile)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun updateDataGuide(nama: String, nomor: String, email: String, alamat: String, image: String) {
        if (binding.edtNamaGuide.text.toString().isEmpty() && binding.edtNomorGuide.text.toString().isEmpty() && binding.edtEmailGuide.text.toString().isEmpty() && binding.edtAlamatGuide.text.toString().isEmpty() && image.isEmpty()) {
            binding.edtNamaGuide.error = "Form tidak boleh kosong"
            binding.edtNomorGuide.error = "Form tidak boleh kosong"
            binding.edtEmailGuide.error = "Form tidak boleh kosong"
            binding.edtAlamatGuide.error = "Form tidak boleh kosong"
            return Toast.makeText(applicationContext, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }

        val extras = intent
        if (extras != null) {
            val id = extras.getStringExtra(EXTRA_ID)
            if (id != null) {
                val ref = FirebaseDatabase.getInstance().getReference("guide")
                val guide = mapOf(
                    "namaGuide" to nama,
                    "nomorGuide" to nomor,
                    "emailGuide" to email,
                    "alamatGuide" to alamat,
                    "fotoGuide" to image
                )
                ref.child(id).updateChildren(guide).addOnSuccessListener {
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

    private fun simpanUpdateGuide() {
        val nama = binding.edtNamaGuide.text.toString()
        val nomor = binding.edtNomorGuide.text.toString()
        val email = binding.edtEmailGuide.text.toString()
        val alamat = binding.edtAlamatGuide.text.toString()

        if (selectedImageUri == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/guide/profil/$fileName")
        ref.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                Log.d("UploadGambar", "Berhasil Upload Gambar: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("UploadGambar", "File Location: $it")
                    updateDataGuide(nama, nomor, email, alamat, it.toString())
                }
            }
            .removeOnFailureListener {
                Log.d("UploadGambar", "Gagal Upload Gambar")
            }
    }
}