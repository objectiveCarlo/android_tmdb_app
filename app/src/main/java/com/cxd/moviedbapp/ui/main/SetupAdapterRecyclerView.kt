package com.cxd.moviedbapp.ui.main

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cxd.moviedbapp.features.movielist.view.MoviesAdapter

@ExperimentalPagingApi
fun setUpAdapterWithErrorAndLoadingViews(moviesAdapter: MoviesAdapter, recyclerView: RecyclerView,
                         progressView: View,
                         swipeRefreshLayout: SwipeRefreshLayout,
                         errorTextView: TextView) {
    recyclerView.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
    }
    recyclerView.adapter = moviesAdapter
    moviesAdapter.addLoadStateListener { loadState ->
        if (loadState.mediator?.refresh is LoadState.Loading) {

            if (moviesAdapter.snapshot().isEmpty()) {
                progressView.isVisible = true
            }
            errorTextView.isVisible = false

        } else {
            progressView.isVisible = false
            swipeRefreshLayout.isRefreshing = false
            val error = when {
                loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                else -> null
            }
            error?.let {
                if (moviesAdapter.snapshot().isEmpty()) {
                    errorTextView.isVisible = true
                    errorTextView.text = it.error.localizedMessage
                }

            }

        }
    }
}