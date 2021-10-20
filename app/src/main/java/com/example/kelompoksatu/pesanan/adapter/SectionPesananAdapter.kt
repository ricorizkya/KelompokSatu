package com.example.kelompoksatu.pesanan.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kelompoksatu.pesanan.ui.fragment.ProsesPesananFragment
import com.example.kelompoksatu.pesanan.ui.fragment.SelesaiPesananFragment

class SectionPesananAdapter(private val context: Context, fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ProsesPesananFragment()
            1 -> fragment = SelesaiPesananFragment()
        }
        return fragment as Fragment
    }
}