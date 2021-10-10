package com.example.kelompoksatu.guide.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.ActivityDetailGuideBinding
import com.example.kelompoksatu.guide.model.Guide
import com.example.kelompoksatu.ui.MainActivity
import com.google.firebase.database.*

class DetailGuideActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailGuideBinding
    private lateinit var query: Query
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        getDetailData()

        binding.btnUpdate.setOnClickListener {
            val sendId = Intent(this, EditGuideActivity::class.java)
            sendId.putExtra(EditGuideActivity.EXTRA_ID, id)
            startActivity(sendId)
        }

        binding.btnDelete.setOnClickListener {
            if (id != null) {
                deleteData(id)
            }
        }
    }

    private fun getDetailData() {
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
                        binding.tvNama.text = guide?.namaGuide
                        binding.tvNomor.text = guide?.nomorGuide
                        binding.tvEmail.text = guide?.emailGuide
                        binding.tvAlamat.text = guide?.alamatGuide
                        Glide.with(applicationContext)
                            .load(guide?.fotoGuide)
                            .into(binding.imgProfile)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun deleteData(id: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("guide")
        databaseReference.child(id).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Data Dihapus", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}