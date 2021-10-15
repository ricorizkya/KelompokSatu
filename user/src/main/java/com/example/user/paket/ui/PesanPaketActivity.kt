package com.example.user.paket.ui

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
import com.example.user.R
import com.example.user.databinding.ActivityPesanPaketBinding
import com.example.user.paket.model.Paket
import com.example.user.paket.model.Pesanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PesanPaketActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityPesanPaketBinding
    private lateinit var query: Query
    var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesanPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataPaket()
        dataInvisible()

        binding.progressCircular.visibility = View.INVISIBLE

        binding.btnImageProfile.setOnClickListener {
            Log.d("UploadKTP", "Pilih Gambar")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityImage.launch(intent)
        }

        binding.btnBayar.setOnClickListener {
            simpanPesanan()
            binding.progressCircular.visibility = View.VISIBLE
        }
    }

    var activityImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
            val image = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            val bitmap = BitmapDrawable(image)
            binding.imgPosterProfile.visibility = View.GONE
            binding.btnImageProfile.setBackgroundDrawable(bitmap)
        }
    }

    private fun getDataPaket() {
        val extras = intent
        val id = intent.getStringExtra(DetailPaketActivity.EXTRA_ID)
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
                            binding.tvGambarPaket.text = paket?.imagePoster
                            binding.tvDurasiPaket.text = paket?.durasi
                            binding.tvAlamatPaket.text = paket?.alamatlengkap
                            binding.tvHargaPaket.text = paket?.harga
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun dataInvisible() {
        binding.tvIdUser.visibility = View.INVISIBLE
        binding.tvEmailUser.visibility = View.INVISIBLE
        binding.tvNamaPaket.visibility = View.INVISIBLE
        binding.tvGambarPaket.visibility = View.INVISIBLE
        binding.tvDurasiPaket.visibility = View.INVISIBLE
        binding.tvAlamatPaket.visibility = View.INVISIBLE
        binding.tvHargaPaket.visibility = View.INVISIBLE
    }

    private fun tambahPesanan(image: String) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val idUser = currentUser?.uid.toString()
        val emailUser = currentUser?.email.toString()
        val emailUserNamaPaket = "${emailUser}" + "${binding.tvNamaPaket.text}"

        if (binding.edtNamaDepanUser.text.toString().isEmpty() && binding.edtNamaBelakangUser.text.toString().isEmpty() && binding.edtNomorUser.text.toString().isEmpty() && binding.edtEmailUser.text.toString().isEmpty() && binding.edtAlamatUser.text.toString().isEmpty() && binding.edtTanggalUser.text!!.isEmpty() && image.isEmpty()) {
            return Toast.makeText(applicationContext, "Form Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
        }

        val ref = FirebaseDatabase.getInstance().getReference("pesanan")
        val pesananId = ref.push().key
        val pesanan = Pesanan(
                pesananId,
                idUser,
                binding.edtNamaDepanUser.text.toString(), binding.edtNamaBelakangUser.text.toString(),
                binding.edtNomorUser.text.toString(), binding.edtEmailUser.text.toString(),
                binding.edtAlamatUser.text.toString(), binding.edtTanggalUser.text.toString(),
                image, binding.tvNamaPaket.text.toString(),
                binding.tvGambarPaket.text.toString(), binding.tvDurasiPaket.text.toString(),
                binding.tvAlamatPaket.text.toString(), binding.tvHargaPaket.text.toString(),
                null, emailUserNamaPaket,
                "Belum Terbayar"
        )

        if (pesananId != null) {
            ref.child(pesananId).setValue(pesanan)
                    .addOnSuccessListener {
                        Log.d("Pesanan", "Input Pesanan Berhasil")
                        val intent = Intent(this, BayarPesananActivity::class.java)
                        intent.putExtra(BayarPesananActivity.EXTRA_ID, pesananId)
                        startActivity(intent)
                    }
        }
    }

    private fun simpanPesanan() {
        if (selectedImageUri == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/user/ktp/$fileName")
        ref.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    Log.d("UploadKTP", "Berhasil Upload KTP: ${it.metadata?.path}")
                    ref.downloadUrl.addOnSuccessListener {
                        tambahPesanan(it.toString())
                    }
                }
                .addOnFailureListener {
                    Log.d("UploadKTP", "Gagal Upload KTP")
                }
    }
}