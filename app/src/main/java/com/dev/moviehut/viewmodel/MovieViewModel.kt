package com.dev.moviehut.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.moviehut.data.model.Movie
import com.dev.moviehut.data.repository.MovieRepository
import com.dev.moviehut.di.DaggerApplicationComponent
import com.dev.moviehut.utils.MovieListUtil
import com.dev.moviehut.utils.Resource
import javax.inject.Inject

class MovieViewModel : ViewModel() {
    @Inject
    lateinit var repository: MovieRepository

    var movies: LiveData<Resource<List<Movie>>>

    var currentMovie = MutableLiveData<Movie?>()

    init {
        DaggerApplicationComponent.create().injectMovieViewModel(this)
        movies = repository.movies
    }

    fun refresh(list: MovieListUtil.MovieList) {
        repository.refresh(list)
    }

    fun getMovies(list: MovieListUtil.MovieList) {
        repository.getMovies(list)
    }

    fun setCurrentMovie(movie: Movie) {
        currentMovie.value = movie
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }

}