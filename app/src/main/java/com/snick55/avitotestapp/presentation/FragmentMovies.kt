package com.snick55.avitotestapp.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.snick55.avitotestapp.R
import com.snick55.avitotestapp.core.simpleScan
import com.snick55.avitotestapp.core.viewBinding
import com.snick55.avitotestapp.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentMovies : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding<FragmentMoviesBinding>()
    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMoviesList()
        setupSearch()
        setupSwipeToRefresh()
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener {

        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupMoviesList() {
        val adapter = MoviesAdapter(onItemClicked = {
            val direction = FragmentMoviesDirections.actionFragmentMoviesToFragmentDetails(it.id)
            findNavController().navigate(direction)
        })
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction = { adapter.retry() })
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        binding.moviesRecyclerView.adapter = adapterWithLoadState
        (binding.moviesRecyclerView.itemAnimator as? DefaultItemAnimator)
            ?.supportsChangeAnimations = false
        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction = { adapter.retry() }
        )
        observeMovies(adapter)
        observeLoadState(adapter)
        handleListVisibility(adapter)
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadState(adapter: MoviesAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleListVisibility(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            getRefreshLoadStateFlow(adapter)
                .simpleScan(count = 3)
                .collectLatest { (beforePrevious, previous, current) ->
                    binding.moviesRecyclerView.isInvisible = current is LoadState.Error
                            || previous is LoadState.Error
                            || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                            && current is LoadState.Loading)
                }
        }
    }

    private fun observeMovies(adapter: MoviesAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movies.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun getRefreshLoadStateFlow(adapter: MoviesAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }
}

