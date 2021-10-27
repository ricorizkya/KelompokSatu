package com.example.user.pesanan.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.example.user.R
import com.example.user.databinding.FragmentPesananBinding
import com.example.user.pesanan.adapter.SectionPesananAdapter
import com.google.android.material.tabs.TabLayoutMediator

class PesananFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.belum_bayar2,
            R.string.dalam_perjalanan
        )
    }

    private lateinit var binding: FragmentPesananBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPesananBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerConfig()
    }

    private fun viewPagerConfig() {
        val sectionPesananAdapter = SectionPesananAdapter(requireActivity().applicationContext, requireActivity())
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPesananAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}