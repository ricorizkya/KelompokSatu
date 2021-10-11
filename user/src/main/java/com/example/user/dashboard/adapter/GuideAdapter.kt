package com.example.user.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.dashboard.model.Guide
import com.example.user.databinding.ListGuideBinding

class GuideAdapter(private val guideList: ArrayList<Guide>): RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    inner class GuideViewHolder(private val binding: ListGuideBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(guide: Guide) {
            with(binding) {
                tvNamaGuide.text = guide.namaGuide
                tvUmurGuide.text = guide.umur
                tvMotto.text = guide.motto
                Glide.with(itemView.context)
                    .load(guide.fotoGuide)
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        val listGuideBinding = ListGuideBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return GuideViewHolder(listGuideBinding)
    }

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        val guide = guideList[position]
        holder.bind(guide)
    }

    override fun getItemCount(): Int = guideList.size
}