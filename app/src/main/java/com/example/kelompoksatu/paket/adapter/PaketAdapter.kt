package com.example.kelompoksatu.paket.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kelompoksatu.databinding.ListPaketBinding
import com.example.kelompoksatu.paket.model.Paket
import com.example.kelompoksatu.paket.ui.DetailPaketActivity

class PaketAdapter(private val listPaket: ArrayList<Paket>): RecyclerView.Adapter<PaketAdapter.PaketViewHolder>() {

    inner class PaketViewHolder(private val binding: ListPaketBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(paket: Paket) {
            with(binding) {
                tvGunung.text = paket.namaPaket
                tvDomisili.text = paket.domisili
                tvDurasi.text = paket.durasi
                tvHarga.text = paket.harga
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaketViewHolder {
        val listPaketBinding = ListPaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaketViewHolder(listPaketBinding)
    }

    override fun onBindViewHolder(holder: PaketViewHolder, position: Int) {
        val paket = listPaket[position]
        holder.bind(paket)
    }

    override fun getItemCount(): Int = listPaket.size
}