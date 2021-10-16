package com.example.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.user.R
import com.example.user.databinding.ActivityMainBinding
import com.example.user.dashboard.ui.DashboardFragment
import com.example.user.paket.ui.fragment.PaketFragment
import com.example.user.pesanan.ui.fragment.PesananFragment
import com.example.user.profile.ui.ProfilFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dashboard = DashboardFragment()
        val paket = PaketFragment()
        val pesanan = PesananFragment()
        val profile = ProfilFragment()

        makeCurrentFragment(dashboard)
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_menu -> makeCurrentFragment(dashboard)
                R.id.paket_menu -> makeCurrentFragment(paket)
                R.id.pesanan_menu -> makeCurrentFragment(pesanan)
                R.id.profil_menu -> makeCurrentFragment(profile)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_content, fragment)
            commit()
        }
    }
}