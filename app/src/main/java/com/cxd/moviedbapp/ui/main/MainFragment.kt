package com.cxd.moviedbapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxd.moviedbapp.databinding.MainFragmentBinding
import com.cxd.moviedbapp.features.movielist.view.MoviesAdapter
import com.cxd.moviedbapp.features.movielist.viewmodels.MovieListViewModel
import kotlinx.coroutines.Job
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
        setUpPopularAdapter()
        setUpComingAdapter()
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

    @ExperimentalPagingApi
    private fun setUpComingAdapter() {
        binding.upcomingVideosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.upcomingVideosRecyclerView.adapter = upcomingMoviesAdapter
        upcomingMoviesAdapter.addLoadStateListener { loadState ->
            if (loadState.mediator?.refresh is LoadState.Loading) {

                if (upcomingMoviesAdapter.snapshot().isEmpty()) {
                    binding.progress.isVisible = true
                }
                binding.errorTxt.isVisible = false

            } else {
                binding.progress.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (upcomingMoviesAdapter.snapshot().isEmpty()) {
                        binding.errorTxt.isVisible = true
                        binding.errorTxt.text = it.error.localizedMessage
                    }

                }

            }
        }
    }

    @ExperimentalPagingApi
    private fun setUpPopularAdapter() {
        binding.popularVideosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.popularVideosRecyclerView.adapter = popularMoviesAdapter
        popularMoviesAdapter.addLoadStateListener { loadState ->
            if (loadState.mediator?.refresh is LoadState.Loading) {

                if (popularMoviesAdapter.snapshot().isEmpty()) {
                    binding.progress.isVisible = true
                }
                binding.errorTxt.isVisible = false

            } else {
                binding.progress.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (popularMoviesAdapter.snapshot().isEmpty()) {
                        binding.errorTxt.isVisible = true
                        binding.errorTxt.text = it.error.localizedMessage
                    }

                }

            }
        }
    }

}