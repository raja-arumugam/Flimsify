package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.data.model.NowPlayingMovies
import com.example.moviesdataapp.databinding.ItemNowPlayingLayoutBinding
import com.example.moviesdataapp.utils.loadImage
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.setLanguage

class NowPlayingMoviesAdapter( val context: Context,
    val list: List<NowPlayingMovies>,
) :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.MyViewHolder>() {

    private var listCount: ArrayList<Int> = ArrayList()
    private lateinit var binding: ItemNowPlayingLayoutBinding
    var onClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding =
            ItemNowPlayingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemNowPlayingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NowPlayingMovies) {
            binding.imageView.loadImage(
                item.backdrop_path,
                ImagePaths.BACKDROP
            )
            binding.tvName.text = item.original_title

            if (!item.adult) {
                binding.tvAdult.text = "U/A"
            } else {
                binding.tvAdult.text = "U"
            }

            binding.tvLanguage.text = context.setLanguage(item.original_language)

            binding.cvNowPlayMovies.setOnClickListener {
                onClick(item.id)
            }
        }
    }
}

