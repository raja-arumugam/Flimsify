package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.MovieVideos
import com.example.moviesdataapp.databinding.LayoutItemMovieVideosBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class MovieVideosListAdapter(
    private val list: List<MovieVideos>
) : RecyclerView.Adapter<MovieVideosListAdapter.MyViewHolder>() {

    private lateinit var layoutItemMovieVideosBinding: LayoutItemMovieVideosBinding
    var onClick: (String) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        layoutItemMovieVideosBinding =
            LayoutItemMovieVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(layoutItemMovieVideosBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: LayoutItemMovieVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieVideos) {

            binding.ivVideo.loadImage(
                item.key,
                ImagePaths.YOUTUBE
            )

            binding.tvType.text = item.type

            binding.cvVideo.setOnClickListener {
                onClick(item.key)
            }
        }
    }

}