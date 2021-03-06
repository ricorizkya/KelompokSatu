package com.example.user.profile.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.user.databinding.FragmentProfilBinding
import com.example.user.paket.model.Paket
import com.example.user.profile.adapter.WishlistAdapter
import com.example.user.ui.LoginActivity
import com.example.user.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding
    private lateinit var wishlistRecyclerView: RecyclerView
    private lateinit var wishlistArrayList: ArrayList<Paket>
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var query: Query
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        binding.tvNameUser.text = currentUser?.displayName
        binding.tvEmailUser.text = currentUser?.email

        lottieAnimationView = binding.imgEmpty
        lottieAnimationView.animate()

        Glide.with(this)
            .load(currentUser?.photoUrl)
            .into(binding.imgProfile)

        if (activity != null) {
            getDataWishlist()
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    private fun getDataWishlist() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email.toString()
        val emailStatus = "$email - Wishlist"

        query = FirebaseDatabase.getInstance().getReference("paket")
                .orderByChild("emailUserStatus")
                .equalTo(emailStatus)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paketSnapshot in snapshot.children) {
                        val wishlitPaket = paketSnapshot.getValue(Paket::class.java)
                        wishlistArrayList.add(wishlitPaket!!)
                    }
                    wishlistRecyclerView.adapter = WishlistAdapter(wishlistArrayList)
                    lottieAnimationView.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                lottieAnimationView.visibility = View.VISIBLE
            }
        })

        wishlistRecyclerView = binding.rvWishlist
        wishlistRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        wishlistRecyclerView.setHasFixedSize(true)
        wishlistArrayList = arrayListOf()
    }
}