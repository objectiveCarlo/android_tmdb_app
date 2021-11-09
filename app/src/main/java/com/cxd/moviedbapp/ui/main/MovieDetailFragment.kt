package com.cxd.moviedbapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cxd.moviedbapp.databinding.MovieDetailFragmentBinding
import com.cxd.moviedbapp.features.favorites.viewmodels.FavoriteMoviesViewModel
import com.cxd.moviedbapp.features.popular.viewmodels.PopularMoviesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MovieDetailFragment: BottomSheetDialogFragment() {
    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: FavoriteMoviesViewModel by activityViewModels()
    private var _binding: MovieDetailFragmentBinding? = null
    private val binding: MovieDetailFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawView()
        setClickListeners()
        subscriptions()
    }
    private fun onLoadCheckIfFavorite(id: Int) {
        viewModel.syncFavorite(id)
    }
    private fun subscriptions() {
        viewModel.isFavorite.observe(this,  {
            binding.favorite.isEnabled = true
            binding.favorite.isChecked = it
        })
    }

    private fun drawView() {
        with(args.movie) {
            binding.overview.text = overview
            binding.releaseDate.text = releaseDate
            binding.title.text = title
            binding.moviePoster.load("https://image.tmdb.org/t/p/w500/$image") {
                crossfade(durationMillis = 1500)
                transformations(RoundedCornersTransformation(12.5f))
            }
            onLoadCheckIfFavorite(id)
        }
    }

    private fun setClickListeners() {
        binding.favorite.setOnClickListener {
            viewModel.favorite(args.movie.id, binding.favorite.isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}