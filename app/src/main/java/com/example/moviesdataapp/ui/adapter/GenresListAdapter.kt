package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.GenresList
import com.example.moviesdataapp.databinding.LayoutItemGenresBinding

class GenresListAdapter(private val list: List<GenresList>) :
    RecyclerView.Adapter<GenresListAdapter.MyViewHolder>() {

    private lateinit var binding: LayoutItemGenresBinding

    class MyViewHolder(val binding: LayoutItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding = LayoutItemGenresBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvGenres.text = list.get(position).name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}