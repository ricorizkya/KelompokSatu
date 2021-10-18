package com.example.user.paket.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.user.R
import com.example.user.R.layout.list_paket
import com.example.user.databinding.FragmentPaketBinding
import com.example.user.databinding.ListPaketBinding
import com.example.user.paket.adapter.PaketAdapter
import com.example.user.paket.model.Paket
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class PaketFragment : Fragment() {

    private lateinit var binding: FragmentPaketBinding
    private lateinit var paketRecyclerView: RecyclerView
    private lateinit var paketArrayList: ArrayList<Paket>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var query: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPaketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            databaseReference = FirebaseDatabase.getInstance().getReference("paket")
            paketRecyclerView.setHasFixedSize(true)
            paketRecyclerView.setLayoutManager(LinearLayoutManager(requireActivity().applicationContext))

        }
    }

}