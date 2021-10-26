package com.example.kelompoksatu.pesanan.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kelompoksatu.databinding.ListPesananBinding
import com.example.kelompoksatu.pesanan.model.Pesanan
import com.example.kelompoksatu.pesanan.ui.DetailPesananActivity

class SelesaiPesananAdapter(private val listPesanan: ArrayList<Pesanan>): RecyclerView.Adapter<SelesaiPesananAdapter.SelesaiPesananViewHolder>() {

    inner class SelesaiPesananViewHolder(private val binding: ListPesananBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pesanan: Pesanan) {
            with(binding) {
                tvBelumBayar.visibility = View.GONE
                tvTanggalIni.text = pesanan.tglBerangkat
                tvNamaPaket.text = pesanan.namaPaket
                tvNamaDepanUser.text = pesanan.namaDepan
                tvNamaBelakangUser.text = pesanan.namaBelakang
                Glide.with(itemView.context)
                        .load(pesanan.fotoKTP)
                        .into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailPesananActivity::class.java)
                    intent.putExtra(DetailPesananActivity.EXTRA_ID, pesanan.idPesanan)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelesaiPesananViewHolder {
        val listSelesaiPesananBinding = ListPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelesaiPesananViewHolder(listSelesaiPesananBinding)
    }

    override fun onBindViewHolder(holder: SelesaiPesananViewHolder, position: Int) {
        val selesaiPesanan = listPesanan[position]
        holder.bind(selesaiPesanan)
    }

    override fun getItemCount(): Int = listPesanan.size
}