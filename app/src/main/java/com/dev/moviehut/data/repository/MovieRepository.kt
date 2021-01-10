package com.dev.moviehut.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.dev.moviehut.MoviehutApplication
import com.dev.moviehut.R
import com.dev.moviehut.data.local.DatabaseService
import com.dev.moviehut.data.model.Movie
import com.dev.moviehut.data.model.MovieData
import com.dev.moviehut.data.remote.NetworkService
import com.dev.moviehut.di.DaggerApplicationComponent
import com.dev.moviehut.utils.MovieListUtil
import com.dev.moviehut.utils.Resource
import com.dev.moviehut.utils.Status
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val networkService: NetworkService) {
    val movies = MutableLiveData<Resource<List<Movie>>>()
    private val compositeDisposable = CompositeDisposable()
    private var databaseService: DatabaseService

    init {
        DaggerApplicationComponent.create().injectMovieRepository(this)
        databaseService = Room.databaseBuilder(
            MoviehutApplication.application,
            DatabaseService::class.java, "moviehut-db"
        ).build()
    }


    fun refresh(movieList: MovieListUtil.MovieList) {
        if (networkService.isNetworkAvailable()) {
            compositeDisposable.add(
                Observable.just(Unit)
                    .doOnNext {
                        databaseService.movieDao().deleteAll()
                    }
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        getMovies(movieList)
                    }
            )
        } else {
            updateResponse(
                Status.ERROR,
                null,
                MoviehutApplication.application.getString(R.string.no_internet_connection)
            )
        }
    }

    fun getMovies(movieList: MovieListUtil.MovieList) {
        val moviesObservable: Single<MovieData> =
            when (movieList) {
                MovieListUtil.MovieList.GET_TOP_RATED ->
                    networkService.getTopRatedMovies()
                MovieListUtil.MovieList.GET_POPULAR ->
                    networkService.getMostPopularMovies()
                MovieListUtil.MovieList.GET_NOW_PLAYING ->
                    networkService.getNowPlayingMovies()
                MovieListUtil.MovieList.GET_LATEST ->
                    networkService.getLatestMovies()
            }
        val apiObservable = moviesObservable
            .map {
                it.results
            }
            .toObservable()
            .doOnNext { movies ->
                movies.forEach { it.movieList.add(movieList) }
                databaseService.movieDao().insertAll(movies)
            }
        val dbObservable = databaseService.movieDao().getAll()
            .flatMap { movies ->
                val filteredList = movies.filter { it.movieList.contains(movieList) }

                return@flatMap Single.just(filteredList).toObservable()
            }

        observeData(dbObservable, apiObservable)
    }

    private fun updateResponse(
        status: Status,
        data: List<Movie>? = null,
        errorMessage: String? = null
    ) {
        val responseLoading = Resource(status, data, errorMessage)
        movies.value = responseLoading
    }

    private fun observeData(db: Observable<List<Movie>>, remote: Observable<List<Movie>>) {
        compositeDisposable.add(
            Observable.concat(db, remote)
                .filter { it.isNotEmpty() }
                .timeout(100, TimeUnit.MILLISECONDS)
                .onErrorResumeNext(remote)
                .firstElement()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    updateResponse(Status.LOADING)
                }
                .subscribe(
                    {
                        updateResponse(Status.SUCCESS, it)
                    },
                    {
                        updateResponse(Status.ERROR, null, it.localizedMessage)
                    }

                ))
    }

    fun clear() {
        compositeDisposable.clear()
    }

}
