package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.model.SeriesCrew
import com.example.moviesdataapp.databinding.LayoutItemCastBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class SeriesCrewListAdapter(private val context: Context, private val list: List<SeriesCrew>) :
    RecyclerView.Adapter<SeriesCrewListAdapter.MyViewHolder>() {

    private lateinit var binding: LayoutItemCastBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding = LayoutItemCastBinding.inflate(
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

    inner class MyViewHolder(val binding: LayoutItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeriesCrew) {
            if (item.profile_path.isNullOrEmpty()) {
                if (item.gender == 1) {
                    Glide.with(context).load(R.drawable.female)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.female)
                        .into(binding.ivPerson)
                } else {
                    Glide.with(context).load(R.drawable.male)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.male)
                        .into(binding.ivPerson)
                }
            } else {
                binding.ivPerson.loadImage(
                    item.profile_path,
                    ImagePaths.BACKDROP
                )
            }

            binding.tvName.text = item.original_name
            binding.tvJob.text = item.department
        }
    }
}