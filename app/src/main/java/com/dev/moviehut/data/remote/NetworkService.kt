package com.dev.moviehut.data.remote

import com.dev.moviehut.BuildConfig.API_KEY
import com.dev.moviehut.data.model.MovieData
import com.dev.moviehut.di.DaggerApplicationComponent
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(private val networkApi: NetworkApi) :
    BaseNetworkService() {

    init {
        DaggerApplicationComponent.create().injectNetworkService(this)
    }

    fun getTopRatedMovies(): Single<MovieData> {
        return networkApi.getTopRatedMovies(API_KEY)
    }

    fun getMostPopularMovies(): Single<MovieData> {
        return networkApi.getMostPopularMovies(API_KEY)
    }

    fun getNowPlayingMovies(): Single<MovieData> {
        return networkApi.getNowPlayingMovies(API_KEY)
    }

    fun getLatestMovies(): Single<MovieData> {
        return networkApi.getLatestMovies(API_KEY)
    }
}
