package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.model.Crew
import com.example.moviesdataapp.databinding.LayoutItemCastBinding
import com.example.moviesdataapp.utils.Constants
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class CrewListAdapter(private val context: Context, private val list: List<Crew>) :
    RecyclerView.Adapter<CrewListAdapter.MyViewHolder>() {

    private lateinit var binding: LayoutItemCastBinding

    class MyViewHolder(val binding: LayoutItemCastBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        val crewList = list[holder.absoluteAdapterPosition]

        if (list[position].profile_path.isNullOrEmpty()) {
            if (list[position].gender == 1) {
                Glide.with(context).load(R.drawable.female).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.female)
                    .into(holder.binding.ivPerson)
            } else {
                Glide.with(context).load(R.drawable.male).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.male)
                    .into(holder.binding.ivPerson)
            }
        } else {
            holder.binding.ivPerson.loadImage(
                list[position].profile_path,
                ImagePaths.BACKDROP
            )
        }

        holder.binding.tvName.text = list[position].original_name
        holder.binding.tvJob.text = list[position].department

    }

    override fun getItemCount(): Int {
        return list.size
    }
}