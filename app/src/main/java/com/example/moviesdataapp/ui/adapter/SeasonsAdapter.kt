package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.Season
import com.example.moviesdataapp.databinding.ItemSeasonsListLayoutBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class SeasonsAdapter(private val list: List<Season>, val tv_id: Int, val name: String) :
    RecyclerView.Adapter<SeasonsAdapter.MyViewHolder>() {

    private lateinit var binding: ItemSeasonsListLayoutBinding
    var onClick: (Int, Int, String) -> Unit = { i: Int, i1: Int, i2: String -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            ItemSeasonsListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemSeasonsListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Season) {
            binding.ivPoster.loadImage(
                item.poster_path,
                ImagePaths.POSTER
            )

            binding.tvMovieName.text = item.name
            binding.tvEpisode.text = " (" + item.episode_count.toString() + " Episodes" + ")"

            binding.root.setOnClickListener {
                onClick(item.season_number, tv_id, name)
            }
        }
    }
}