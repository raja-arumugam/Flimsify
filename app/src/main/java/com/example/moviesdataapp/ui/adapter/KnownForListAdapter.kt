package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.model.ActorMovies
import com.example.moviesdataapp.databinding.ItemListLayoutBinding
import com.example.moviesdataapp.utils.Constants
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class KnownForListAdapter(val context: Context, val list: List<ActorMovies>) :
    RecyclerView.Adapter<KnownForListAdapter.MyViewHolder>() {

    private lateinit var binding: ItemListLayoutBinding
    var onClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            ItemListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActorMovies) {

            if (item.poster_path.isNullOrEmpty()) {
                binding.cvItem.visibility = View.GONE
                binding.ivMoviePost.visibility = View.GONE
                binding.tvMovieName.visibility = View.GONE
            } else {
                binding.cvItem.visibility = View.VISIBLE
                binding.ivMoviePost.visibility = View.VISIBLE
                binding.tvMovieName.visibility = View.VISIBLE

                binding.ivMoviePost.loadImage(
                    item.poster_path,
                    ImagePaths.POSTER
                )
            }

            if (item.original_title.isNullOrEmpty()) {
                binding.tvMovieName.visibility = View.GONE
            } else {
                binding.tvMovieName.visibility = View.VISIBLE
                binding.tvMovieName.text = item.title
            }

            binding.root.setOnClickListener {
                onClick(item.id)
            }

        }
    }
}