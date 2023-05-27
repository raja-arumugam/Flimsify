package com.example.moviesdataapp.ui.adapter

import android.R.attr.level
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.model.MovieGenreList
import com.example.moviesdataapp.databinding.LayoutExploreGenreListItemBinding


class ExploreGenreListAdapter(
    private val context: Context,
    private val list: List<MovieGenreList>
) :
    RecyclerView.Adapter<ExploreGenreListAdapter.ViewHolder>() {

    private lateinit var binding: LayoutExploreGenreListItemBinding
    var onClick: (String, Int, Int) -> Unit = { s: String, i: Int, j: Int -> }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreGenreListAdapter.ViewHolder {

        binding = LayoutExploreGenreListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ExploreGenreListAdapter.ViewHolder, position: Int) {
        val genre_Imgs = context.resources.obtainTypedArray(R.array.genre_images)
        val count: Int = genre_Imgs.length()
        val ids = IntArray(count)
        for (i in ids.indices) {
            ids[i] = genre_Imgs.getResourceId(i, 0)
        }
        genre_Imgs.recycle();

        holder.bind(list[holder.absoluteAdapterPosition], ids)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: LayoutExploreGenreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieGenreList, img: IntArray) {

            Glide.with(context).load(img[absoluteAdapterPosition])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.gray_placeholder)
                .into(binding.ivGenre)

            binding.tvGenres.text = item.name

            binding.root.setOnClickListener {
                onClick(item.name, item.id, img[absoluteAdapterPosition])
            }

        }
    }
}