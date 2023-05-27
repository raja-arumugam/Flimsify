package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.SimilarMovie
import com.example.moviesdataapp.databinding.ItemSimilarLayoutBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class SimilarMoviesAdapter(private val list: List<SimilarMovie>) :
    RecyclerView.Adapter<SimilarMoviesAdapter.MyViewHolder>() {

    private lateinit var binding: ItemSimilarLayoutBinding
    var onClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarMoviesAdapter.MyViewHolder {
        binding = ItemSimilarLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMoviesAdapter.MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemSimilarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimilarMovie) {
            binding.ivMoviePost.loadImage(
                item.poster_path,
                ImagePaths.POSTER
            )

            binding.tvMovieName.visibility = View.VISIBLE
            binding.tvMovieName.text = item.original_title

            binding.root.setOnClickListener {
                onClick(item.id)
            }

        }
    }

}