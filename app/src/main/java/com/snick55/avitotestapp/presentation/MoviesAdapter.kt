package com.snick55.avitotestapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snick55.avitotestapp.R
import com.snick55.avitotestapp.databinding.ItemMovieBinding
import com.snick55.avitotestapp.domain.Movie

class MoviesAdapter(
    private val onItemClicked: (Movie) -> Unit
) : PagingDataAdapter<Movie, MoviesAdapter.Holder>(MoviesDiffCallback()),
    View.OnClickListener {

    class Holder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                root.tag = movie
                nameTV.text = movie.name
                yearTV.text = binding.root.context.getString(R.string.year_title,movie.year.toString())
                countryTV.text = binding.root.context.getString(R.string.country_title,movie.countries)
                ageRatingTV.text = binding.root.context.getString(R.string.age_rating_title,movie.ageRating.toString())
                descriptionTV.text = movie.shortDescription
                Glide.with(binding.root.context).load(movie.poster).into(poster)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onClick(v: View) {
        val movie = v.tag as Movie
        onItemClicked.invoke(movie)
    }
}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}