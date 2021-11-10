package com.cxd.moviedbapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.cxd.moviedbapp.databinding.MainFragmentBinding
import com.cxd.moviedbapp.features.movielist.view.MoviesAdapter
import com.cxd.moviedbapp.features.movielist.viewmodels.MovieListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

    private val viewModel: MovieListViewModel by activityViewModels()
    private val popularMoviesAdapter = MoviesAdapter()
    private val upcomingMoviesAdapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapterWithErrorAndLoadingViews(popularMoviesAdapter,
            binding.popularVideosRecyclerView,
            binding.progress,
            binding.swipeRefreshLayout,
            binding.errorTxt)

        setUpAdapterWithErrorAndLoadingViews(upcomingMoviesAdapter,
            binding.upcomingVideosRecyclerView,
            binding.progress,
            binding.swipeRefreshLayout,
            binding.errorTxt)
        startListJobs()
        binding.swipeRefreshLayout.setOnRefreshListener {
            popularMoviesAdapter.refresh()
            upcomingMoviesAdapter.refresh()
        }
    }

    @ExperimentalPagingApi
    private fun startListJobs() {
        lifecycleScope.launch {
            viewModel.getPopularMovies()
                .collectLatest {
                    popularMoviesAdapter.submitData(it)
                }

        }
        lifecycleScope.launch {
            viewModel.getUpcomingMovies()
                .collectLatest {
                    upcomingMoviesAdapter.submitData(it)
                }
        }

    }

}