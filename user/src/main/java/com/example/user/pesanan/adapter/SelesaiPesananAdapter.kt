package com.example.user.pesanan.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.databinding.ListPesananBinding
import com.example.user.pesanan.model.Pesanan
import com.example.user.pesanan.ui.BayarPesananActivity
import com.example.user.pesanan.ui.DetailPesananActivity
import com.google.firebase.auth.FirebaseAuth

class SelesaiPesananAdapter(private val selesaiList: ArrayList<Pesanan>): RecyclerView.Adapter<SelesaiPesananAdapter.SelesaiViewHolder>() {

    inner class SelesaiViewHolder(private val binding: ListPesananBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pesanan: Pesanan) {
            with(binding) {
                tvDalamPerjalanan.visibility = View.GONE
                tvBelumBayar.visibility = View.GONE

                tvTanggalIni.text = pesanan.tglIni
                tvNamaPaket.text = pesanan.namaPaket
                tvDurasiPaket.text = pesanan.durasiPaket
                Glide.with(itemView.context)
                        .load(pesanan.imagePaket)
                        .into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailPesananActivity::class.java)
                    intent.putExtra(DetailPesananActivity.EXTRA_ID, pesanan.idPesanan)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelesaiViewHolder {
        val listSelesaiPesanan = ListPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelesaiViewHolder(listSelesaiPesanan)
    }

    override fun onBindViewHolder(holder: SelesaiViewHolder, position: Int) {
        val selesaiPesanan = selesaiList[position]
        holder.bind(selesaiPesanan)
    }

    override fun getItemCount(): Int = selesaiList.size
}