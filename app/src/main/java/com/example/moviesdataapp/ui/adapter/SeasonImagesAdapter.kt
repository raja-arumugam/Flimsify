package com.example.moviesdataapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.databinding.ItemNowPlayingLayoutBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class SeasonImagesAdapter(
    val list: List<BackdropImages>,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<SeasonImagesAdapter.MyViewHolder>() {

    private lateinit var binding: ItemNowPlayingLayoutBinding
    private var listCount: ArrayList<Int> = ArrayList()
    var onClick: (List<BackdropImages>, Int, Boolean) -> Unit = { param: List<BackdropImages>, i: Int, j: Boolean -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            ItemNowPlayingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])

        if (position == listCount.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemNowPlayingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BackdropImages) {
            binding.tvName.visibility = View.GONE
            binding.llPlay.visibility = View.GONE
            binding.tvLanguage.visibility = View.GONE
            binding.ivDot.visibility = View.GONE
            binding.tvAdult.visibility = View.GONE

            binding.imageView.loadImage(
                item.file_path,
                ImagePaths.BACKDROP
            )

            binding.root.setOnClickListener {
                onClick(list, absoluteAdapterPosition, true)
            }
        }
    }

    private val runnable = Runnable {
        notifyDataSetChanged()
    }
}