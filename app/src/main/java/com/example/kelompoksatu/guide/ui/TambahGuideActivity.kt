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
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.ActivityTambahGuideBinding
import com.example.kelompoksatu.guide.model.Guide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class TambahGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahGuideBinding
    var selectedImageProfilUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.progressCircular.visibility = View.INVISIBLE

        binding.btnImageProfile.setOnClickListener {
            Log.d("Upload Gambar", "Pilih Gambar")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityImage.launch(intent)
        }

        binding.btnAdd.setOnClickListener {
            simpanGuide()
            binding.progressCircular.visibility = View.VISIBLE
        }
    }

    var activityImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageProfilUri = data?.data
            val image = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageProfilUri)
            val bitmap = BitmapDrawable(image)
            binding.imgPosterProfile.visibility = View.GONE
            binding.btnImageProfile.setBackgroundDrawable(bitmap)
        }
    }

    private fun tambahGuide(imageProfil: String) {
        if (binding.edtNamaGuide.text.toString().isEmpty() &&
            binding.edtNomorGuide.text.toString().isEmpty() &&
            binding.edtEmailGuide.text.toString().isEmpty() &&
            binding.edtNomorGuide.text.toString().isEmpty() &&
            binding.edtAlamatGuide.text.toString().isEmpty() &&
            binding.edtMotoGuide.text.toString().isEmpty() &&
            imageProfil.isEmpty()) {

            binding.edtNamaGuide.error = "Form tidak boleh kosong"
            binding.edtNomorGuide.error = "Form tidak boleh kosong"
            binding.edtEmailGuide.error = "Form tidak boleh kosong"
            binding.edtAlamatGuide.error = "Form tidak boleh kosong"
            binding.edtUmurGuide.error = "Form tidak boleh kosong"
            binding.edtMotoGuide.error = "Form tidak boleh kosong"

            return Toast.makeText(applicationContext, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }

        val ref = FirebaseDatabase.getInstance().getReference("guide")
        val guideId = ref.push().key
        val paket = Guide(
            guideId,
            binding.edtNamaGuide.text.toString(),
            binding.edtNomorGuide.text.toString(),
            binding.edtEmailGuide.text.toString(),
            binding.edtAlamatGuide.text.toString(),
            binding.edtUmurGuide.text.toString(),
            imageProfil,
            binding.edtMotoGuide.text.toString()
        )

        if (guideId != null) {
            ref.child(guideId).setValue(paket)
                .addOnSuccessListener {
                    Log.d("InputGuide", "Berhasil Input Data Guide")
                    Toast.makeText(applicationContext, "Data Guide Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, GuideFragment::class.java))
                    finish()
                }
        }
    }

    private fun simpanGuide() {
        if (selectedImageProfilUri == null) return
        val fileNameProfil = UUID.randomUUID().toString()
        val fileNameKTP = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/guide/profil/$fileNameProfil")
        ref.putFile(selectedImageProfilUri!!)
            .addOnSuccessListener {
                Log.d("UploadGambar", "Berhasil Upload Gambar: ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("UploadGambar", "File Location: $it")
                    tambahGuide(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("Upload Gambar", "Gagal Upload Gambar")
            }
    }
}