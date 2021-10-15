package com.example.user.paket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.user.R

class BayarPesananActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bayar_pesanan)
    }
}