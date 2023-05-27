package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.Episode
import com.example.moviesdataapp.databinding.ItemSimilarLayoutBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class EpisodeAdapter(private val list: List<Episode>) :
    RecyclerView.Adapter<EpisodeAdapter.MyViewHolder>() {

    private lateinit var binding: ItemSimilarLayoutBinding
    var onClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeAdapter.MyViewHolder {
        binding =
            ItemSimilarLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeAdapter.MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemSimilarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Episode) {
            binding.ivMoviePost.loadImage(
                item.still_path,
                ImagePaths.POSTER
            )

            binding.tvMovieName.visibility = View.VISIBLE
            binding.tvMovieName.text = item.name

            binding.root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

}