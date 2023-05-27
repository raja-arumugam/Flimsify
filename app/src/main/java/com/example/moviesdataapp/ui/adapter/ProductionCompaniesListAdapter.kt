package com.example.moviesdataapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdataapp.data.model.ProductionCompanies
import com.example.moviesdataapp.databinding.ItemLayoutProductionCompaniesBinding
import com.example.moviesdataapp.utils.Constants
import com.example.moviesdataapp.utils.ImagePaths
import com.example.moviesdataapp.utils.loadImage

class ProductionCompaniesListAdapter(
    val context: Context,
    private val list: List<ProductionCompanies>
) :
    RecyclerView.Adapter<ProductionCompaniesListAdapter.MyViewHolder>() {

    private lateinit var binding: ItemLayoutProductionCompaniesBinding

    class MyViewHolder(val binding: ItemLayoutProductionCompaniesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding = ItemLayoutProductionCompaniesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val productionCompanies = list[holder.absoluteAdapterPosition]

        if (list[position].logo_path.isNullOrEmpty()) {
            holder.binding.ivCompany.visibility = View.GONE
            holder.binding.tvName.visibility = View.GONE
        } else {
            holder.binding.ivCompany.visibility = View.VISIBLE
            holder.binding.tvName.visibility = View.VISIBLE

            holder.binding.tvName.text = list[position].name

            holder.binding.ivCompany.loadImage(
                list[position].logo_path,
                ImagePaths.POSTER
            )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}