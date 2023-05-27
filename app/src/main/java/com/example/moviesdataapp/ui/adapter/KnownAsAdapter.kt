package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.databinding.ItemListTextLayoutBinding

class KnownAsAdapter(private val list: List<String>) :
    RecyclerView.Adapter<KnownAsAdapter.MyViewHolder>() {

    private lateinit var listTextLayoutBinding: ItemListTextLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        listTextLayoutBinding =
            ItemListTextLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(listTextLayoutBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val knowsAsList = list[holder.absoluteAdapterPosition]
        holder.binding.tvName.text = knowsAsList
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(val binding: ItemListTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}