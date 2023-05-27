package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.PopularMovies
import com.example.moviesdataapp.databinding.ItemPopularListLayoutBinding
import com.example.moviesdataapp.utils.Constants
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class PopularListAdapter(val context: Context, val list: List<PopularMovies>) :
    RecyclerView.Adapter<PopularListAdapter.MyViewHolder>() {

    private lateinit var binding: ItemPopularListLayoutBinding
    var onClickItem: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            ItemPopularListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemPopularListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularMovies) {
            binding.ivPopular.loadImage(
                item.poster_path,
                ImagePaths.POSTER
            )

            binding.root.setOnClickListener {
                onClickItem(item.id)
            }

        }
    }
}