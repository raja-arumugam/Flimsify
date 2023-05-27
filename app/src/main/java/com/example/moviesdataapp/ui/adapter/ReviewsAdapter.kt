package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesdataapp.R
import com.example.moviesdataapp.data.model.ReviewList
import com.example.moviesdataapp.databinding.ItemLayoutReviewBinding
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ReviewsAdapter(private val context: Context, private val list: List<ReviewList>) :
    RecyclerView.Adapter<ReviewsAdapter.MyViewHolder>() {

    private lateinit var binding: ItemLayoutReviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            ItemLayoutReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[holder.absoluteAdapterPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemLayoutReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewList) {

            if (item.author_details!!.avatar_path == null || item.author_details.avatar_path!!.substring(0).contains("/http")) {
                Glide.with(context).load(R.drawable.male).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.male)
                    .into(binding.ivPerson)
            } else {
                binding.ivPerson.loadImage(
                    item.author_details.avatar_path,
                    ImagePaths.REVIEW
                )

            }

            binding.tvName.text = item.author_details.username
            binding.tvReview.text = item.content
            binding.tvRating.text = item.author_details.rating.toString()

            val dateString = item.created_at
            val result =
                dateString!!.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
            val inputDateStr = result
            val date: Date = inputFormat.parse(inputDateStr) as Date
            val finalReleaseDate: String = outputFormat.format(date)
            binding.tvDate.text = finalReleaseDate
        }
    }
}