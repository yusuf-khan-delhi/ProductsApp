package com.example.demo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.databinding.ListItemBinding
import com.example.demo.db.ItemResponse

class ItemsAdapter(var context: Context?, private var listOfItems: List<ItemResponse?>?,
    private val onItemClicked: (ItemResponse) -> Unit)
    : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listOfItems?.get(position)) {
               binding.tvProductTittle.text = this?.title
               binding.tvPrice.text = context?.resources?.getString(R.string.rupee_symbol).plus(" ").plus(this?.price.toString())
               binding.tvRating.text = this?.rating?.rate.toString()
               binding.tvRatingCount.text = this?.rating?.count.toString()
               binding.overallRating.rating = this?.rating?.rate?.toFloat()!!
               context?.let {
                    Glide.with(it)
                        .load(this.image)
                        .into(binding.imageView)
                }

                binding.root.setOnClickListener {
                    onItemClicked(this)
                }
            }
        }
    }

    // return the size of the list
    override fun getItemCount(): Int {
        return listOfItems?.size ?: 0
    }
}