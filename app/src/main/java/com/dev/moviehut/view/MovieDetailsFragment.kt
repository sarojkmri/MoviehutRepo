package com.dev.moviehut.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dev.moviehut.R
import com.dev.moviehut.data.model.Movie
import com.dev.moviehut.data.remote.EndPoints
import com.dev.moviehut.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.movie_details.*

class MovieDetailsFragment : Fragment() {
    private val moviesViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        moviesViewModel.currentMovie.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                updateMovieData(it)
            }
        })
    }

    private fun updateMovieData(movie: Movie) {
        Glide.with(requireActivity())
            .load(EndPoints.PHOTO_BASE_URL + movie.backdropPath)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(backdropImageView)
        movieNameTextView.text = movie.title
        movieDateTextView.text = movie.releaseDate
        movieVoteAverageTextView.text = movie.voteAverage.toString()
        movieVoteCountTextView.text = movie.voteCount.toString()
        movieOverviewTextView.text = movie.overview
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieDetailsFragment()
    }
}