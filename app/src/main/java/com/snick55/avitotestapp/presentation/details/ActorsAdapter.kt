package com.snick55.avitotestapp.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snick55.avitotestapp.R
import com.snick55.avitotestapp.databinding.ItemActorBinding
import com.snick55.avitotestapp.domain.entities.Actor

class ActorsAdapter :
    ListAdapter<Actor, ActorsAdapter.ActorsViewHolder>(DetailUiDiffCallback()) {


    inner class ActorsViewHolder(private val binding: ItemActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            binding.nameTV.text = actor.name
            binding.roleTV.text = actor.description
            Glide.with(binding.photo.context).load(actor.photo)
                .placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                .into(binding.photo)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemActorBinding.inflate(inflater, parent, false)
        return ActorsViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ActorsViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class DetailUiDiffCallback : DiffUtil.ItemCallback<Actor>() {

        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Actor,
            newItem: Actor
        ): Boolean {
            return oldItem == newItem
        }
    }
}