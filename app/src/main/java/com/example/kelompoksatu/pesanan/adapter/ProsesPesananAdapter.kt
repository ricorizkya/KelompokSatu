package com.example.kelompoksatu.pesanan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kelompoksatu.databinding.ListPesananBinding
import com.example.kelompoksatu.pesanan.model.Pesanan

class ProsesPesananAdapter(private val listPesanan: ArrayList<Pesanan>): RecyclerView.Adapter<ProsesPesananAdapter.ProsesPesananViewHolder>() {

    inner class ProsesPesananViewHolder(private val binding: ListPesananBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pesanan: Pesanan) {
            with(binding) {
                tvSelesai.visibility = View.GONE
                tvTanggalIni.text = pesanan.tglBerangkat
                tvNamaPaket.text = pesanan.namaPaket
                tvNamaDepanUser.text = pesanan.namaDepan
                tvNamaBelakangUser.text = pesanan.namaBelakang
                Glide.with(itemView.context)
                        .load(pesanan.fotoKTP)
                        .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProsesPesananViewHolder {
        val listProsesPesananBinding = ListPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProsesPesananViewHolder(listProsesPesananBinding)
    }

    override fun onBindViewHolder(holder: ProsesPesananViewHolder, position: Int) {
        val prosesPesanan = listPesanan[position]
        holder.bind(prosesPesanan)
    }

    override fun getItemCount(): Int = listPesanan.size
}