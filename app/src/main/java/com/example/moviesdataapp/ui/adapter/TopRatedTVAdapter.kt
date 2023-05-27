package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.data.model.TopRatedTV
import com.example.moviesdataapp.databinding.ItemNowPlayingLayoutBinding
import com.example.moviesdataapp.utils.Constants
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage
import com.example.moviesdataapp.utils.setLanguage

class TopRatedTVAdapter(
    private val context: Context,
    private val list: List<TopRatedTV>,
) : RecyclerView.Adapter<TopRatedTVAdapter.MyViewHolder>() {

//    private val viewPager2: ViewPager2

    private lateinit var binding: ItemNowPlayingLayoutBinding
    var onClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // Inflate UI layout items from Now Playing Movies alone
        binding =
            ItemNowPlayingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])

       /* if (position == list.size - 1) {
            viewPager2.post(runnable)
        }*/
    }

    inner class MyViewHolder(val binding: ItemNowPlayingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: TopRatedTV) {
                binding.imageView.loadImage(
                    item.backdrop_path,
                    ImagePaths.POSTER
                )

                binding.tvName.text = item.original_name
                binding.tvAdult.text = item.vote_average.toString() + "%"

                binding.tvLanguage.text = context.setLanguage(item.original_language)

                binding.root.setOnClickListener {
                    onClick(item.id)
                }

            }
        }

    override fun getItemCount(): Int {
        return list.size
    }

    /*private val runnable = Runnable {
        notifyDataSetChanged()
    }*/

}