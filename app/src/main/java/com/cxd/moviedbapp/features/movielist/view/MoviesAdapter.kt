package com.cxd.moviedbapp.features.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cxd.moviedbapp.common.models.domain.Movie
import com.cxd.moviedbapp.databinding.AdapterItemBinding
import com.cxd.moviedbapp.ui.main.MainFragmentDirections

class MoviesAdapter: PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(
        MovieDiffCallback()
    ) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val holder = MovieViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        holder.binding.root.setOnClickListener { view ->
            getItem(holder.bindingAdapterPosition)?.let { movie ->
                view.findNavController().navigate(
                    MainFragmentDirections.actionGoToDetail( movie = movie)
                )
            }
        }

        return holder
    }

    inner class MovieViewHolder(
        val binding: AdapterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie?) {
            binding.let {
                binding.title.text = data?.title
                data?.image?.let {
                    binding.imageView.load("https://image.tmdb.org/t/p/w500/$it") {
                        crossfade(durationMillis = 2000)
                        transformations(RoundedCornersTransformation(12.5f))
                    }
                }

            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}