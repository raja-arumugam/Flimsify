package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.KollyWoodMoviesList
import com.example.moviesdataapp.databinding.ItemGenresMoviesListLayoutBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class KollywoodMoviesAdapter(val context: Context, val list: List<KollyWoodMoviesList>) :
    RecyclerView.Adapter<KollywoodMoviesAdapter.MyViewHolder>() {

    lateinit var binding: ItemGenresMoviesListLayoutBinding
    var onClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KollywoodMoviesAdapter.MyViewHolder {

        binding =
            ItemGenresMoviesListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemGenresMoviesListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KollyWoodMoviesList) {

            binding.tvName.text = item.title
            binding.tvDescription.text = item.overview

            binding.ivGenreMovie.loadImage(
                item.poster_path,
                ImagePaths.BACKDROP
            )

            binding.root.setOnClickListener {
                onClick(item.id)
            }

        }
    }
}