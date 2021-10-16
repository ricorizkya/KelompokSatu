package com.example.user.pesanan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.databinding.ListPesananBinding
import com.example.user.pesanan.model.Pesanan
import com.google.firebase.auth.FirebaseAuth

class ProsesPesananAdapter(private val prosesList: ArrayList<Pesanan>): RecyclerView.Adapter<ProsesPesananAdapter.ProsesPesananViewHolder>() {

    inner class ProsesPesananViewHolder(private val binding: ListPesananBinding): RecyclerView.ViewHolder(binding.root) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email
        val idUserStatus = "Belum Bayar - $email"

        fun bind(pesanan: Pesanan) {
            with(binding) {
                    tvDalamPerjalanan.visibility = View.GONE
                    tvSelesai.visibility = View.GONE

                    tvTanggalIni.text = pesanan.tglIni
                    tvNamaPaket.text = pesanan.namaPaket
                    tvDurasiPaket.text = pesanan.durasiPaket
                    Glide.with(itemView.context)
                        .load(pesanan.imagePaket)
                        .into(imgPoster)
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProsesPesananAdapter.ProsesPesananViewHolder {
        val listProsesPesananBinding = ListPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProsesPesananViewHolder(listProsesPesananBinding)
    }

    override fun onBindViewHolder(holder: ProsesPesananViewHolder, position: Int) {
        val prosesPesanan = prosesList[position]
        holder.bind(prosesPesanan)
    }

    override fun getItemCount(): Int = prosesList.size
}