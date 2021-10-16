package com.example.user.pesanan.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.user.pesanan.ui.fragment.PesananProsesFragment
import com.example.user.pesanan.ui.fragment.PesananSelesaiFragment

class SectionPesananAdapter(private val context: Context, fragmentActivity: FragmentActivity)
    :FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PesananProsesFragment()
            1 -> fragment = PesananSelesaiFragment()
        }
        return fragment as Fragment
    }
}