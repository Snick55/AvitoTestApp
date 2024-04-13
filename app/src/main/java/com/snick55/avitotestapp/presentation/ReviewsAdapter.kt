package com.snick55.avitotestapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.snick55.avitotestapp.databinding.ItemReviewBinding
import com.snick55.avitotestapp.domain.Review

class ReviewsAdapter(
) : PagingDataAdapter<Review, ReviewsAdapter.Holder>(ReviewsDiffCallback()){

    class Holder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.authorTV.text = review.author
            binding.createdAtTV.text = review.createdAt
            binding.descriptionTV.text = review.review
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val review = getItem(position) ?: return
        holder.bind(review)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }


}

class ReviewsDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.author == newItem.author
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}