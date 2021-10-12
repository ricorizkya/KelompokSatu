package com.example.user.profile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.databinding.ListHotPaketBinding
import com.example.user.paket.model.Paket
import com.example.user.paket.ui.DetailPaketActivity

class WishlistAdapter(private val listWishlist: ArrayList<Paket>): RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(private val binding: ListHotPaketBinding): RecyclerView.ViewHolder(binding.root) {
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
                    intent.putExtra(DetailPaketActivity.EXTRA_TITLE, paket.namaPaket)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val listHotPaketBinding = ListHotPaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistViewHolder(listHotPaketBinding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val wishlist = listWishlist[position]
        holder.bind(wishlist)
    }

    override fun getItemCount(): Int = listWishlist.size
}