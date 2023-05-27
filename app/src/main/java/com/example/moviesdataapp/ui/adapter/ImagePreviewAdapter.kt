package com.example.moviesdataapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.databinding.ItemImagePreviewBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class ImagePreviewAdapter(val list: Array<BackdropImages>) :
    RecyclerView.Adapter<ImagePreviewAdapter.MyViewHolder>() {

    private lateinit var binding: ItemImagePreviewBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding =
            ItemImagePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemImagePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BackdropImages) {
            binding.imageView.maxWidth = item.width
            binding.imageView.loadImage(
                item.file_path,
                ImagePaths.BACKDROP
            )


        }
    }

}