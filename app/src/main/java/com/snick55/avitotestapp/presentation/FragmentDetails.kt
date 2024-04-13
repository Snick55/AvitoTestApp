package com.snick55.avitotestapp.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.snick55.avitotestapp.R
import com.snick55.avitotestapp.core.observe
import com.snick55.avitotestapp.core.simpleScan
import com.snick55.avitotestapp.core.viewBinding
import com.snick55.avitotestapp.databinding.FragmentDetailsBinding
import com.snick55.avitotestapp.domain.MovieDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentDetails: Fragment(R.layout.fragment_details) {

    private val binding by viewBinding<FragmentDetailsBinding>()
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<FragmentDetailsArgs>()
    private var indexImage = 0
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentMovieId = args.movieId
        viewModel.getMovieDetails(currentMovieId)
        viewModel.getReviews(currentMovieId)
        val adapter = ActorsAdapter()
        binding.actorsRV.adapter = adapter
        setupReviewsList()

        binding.resutlView.observe(viewLifecycleOwner,viewModel.movie){movieDetail->
            adapter.submitList(movieDetail.actors)
            binding.nameTV.text = getString(R.string.name_title,movieDetail.name)
            binding.descriptionTV.text = movieDetail.description
            binding.ratingTV.text = getString(R.string.rating_title,movieDetail.rating.toString())
            setupImages(movieDetail)
            binding.rightArrow.setOnClickListener {
                indexImage += 1
                setupImages(movieDetail)
            }
            binding.leftArrow.setOnClickListener {
                indexImage -= 1
                setupImages(movieDetail)
            }
        }
        binding.resutlView.setTryAgainListener {
            viewModel.getMovieDetails(currentMovieId)
        }
    }

    private fun setupReviewsList() {
        val adapter = ReviewsAdapter()
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction = {adapter.retry()})
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        binding.reviewsRV.adapter = adapterWithLoadState
        binding.reviewsRV.adapter = adapterWithLoadState
        (binding.reviewsRV.itemAnimator as? DefaultItemAnimator)
            ?.supportsChangeAnimations = false
        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
           null,
            tryAgainAction = { adapter.retry() }
        )
        observeReviews(adapter)
        observeLoadState(adapter)
        handleListVisibility(adapter)
    }

    private fun handleListVisibility(adapter: ReviewsAdapter) =
        viewLifecycleOwner.lifecycleScope.launch {
            getRefreshLoadStateFlow(adapter)
                .simpleScan(count = 3)
                .collectLatest { (beforePrevious, previous, current) ->
                    binding.reviewsRV.isInvisible = current is LoadState.Error
                            || previous is LoadState.Error
                            || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                            && current is LoadState.Loading)
                }
        }

    private fun getRefreshLoadStateFlow(adapter: ReviewsAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadState(adapter: ReviewsAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun observeReviews(adapter: ReviewsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.reviews.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setupImages(movie: MovieDetail) {
        binding.rightArrow.visibility =
            if (indexImage == movie.posters.size - 1) View.GONE else View.VISIBLE
        binding.leftArrow.visibility = if (indexImage == 0) View.GONE else View.VISIBLE
        Glide.with(requireContext())
            .load(movie.posters[indexImage])
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .fitCenter()
            .into(binding.images)

    }

}