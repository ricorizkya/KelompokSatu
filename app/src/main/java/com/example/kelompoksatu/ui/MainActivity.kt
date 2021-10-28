package com.example.kelompoksatu.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.kelompoksatu.R
import com.example.kelompoksatu.databinding.ActivityMainBinding
import com.example.kelompoksatu.guide.ui.GuideFragment
import com.example.kelompoksatu.paket.ui.PaketFragment
import com.example.kelompoksatu.pesanan.ui.fragment.PesananFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        val paketFragment = PaketFragment()
        val pesananFragment = PesananFragment()
        val guideFragment = GuideFragment()

        makeCurrentFragment(paketFragment)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.paket_menu -> makeCurrentFragment(paketFragment)
                R.id.pesanan_menu -> makeCurrentFragment(pesananFragment)
                R.id.guide_menu -> makeCurrentFragment(guideFragment)
            }
            true
        }
    }

    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_content, fragment)
            commit()
        }
    }
}