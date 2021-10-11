package com.example.user.dashboard.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.databinding.ListHotPaketBinding
import com.example.user.paket.model.Paket
import com.example.user.paket.ui.DetailPaketActivity

class HotPaketAdapter(private val listHotPaket: ArrayList<Paket>): RecyclerView.Adapter<HotPaketAdapter.HotPaketViewHolder>() {

    inner class HotPaketViewHolder(private val binding: ListHotPaketBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(paket: Paket) {
            with(binding) {
                tvNamaPaket.text = paket.namaPaket
                tvDomisiliPaket.text = paket.domisili
                Glide.with(itemView.context)
                        .load(paket.imagePoster)
                        .into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailPaketActivity::class.java)
                    intent.putExtra(DetailPaketActivity.EXTRA_ID, paket.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotPaketViewHolder {
        val listHotPaketBinding = ListHotPaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotPaketViewHolder(listHotPaketBinding)
    }

    override fun onBindViewHolder(holder: HotPaketViewHolder, position: Int) {
        val hotPaket = listHotPaket[position]
        holder.bind(hotPaket)
    }

    override fun getItemCount(): Int {
        return 5
    }
}