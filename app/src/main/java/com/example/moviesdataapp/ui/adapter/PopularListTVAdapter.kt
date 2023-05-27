package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.PopularTv
import com.example.moviesdataapp.databinding.ItemPopularListLayoutBinding
import com.example.moviesdataapp.utils.Constants
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

//This adapter is using both Movie API and TV API
class PopularListTVAdapter(val context: Context, val list: List<PopularTv>) :
    RecyclerView.Adapter<PopularListTVAdapter.MyViewHolder>() {

    private lateinit var binding: ItemPopularListLayoutBinding
    var onClick: (Int) -> Unit = {}

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
            fun bind(item: PopularTv) {
                binding.ivPopular.loadImage(
                    item.poster_path,
                    ImagePaths.POSTER
                )

                binding.root.setOnClickListener {
                    onClick(item.id)
                }
            }
        }

}