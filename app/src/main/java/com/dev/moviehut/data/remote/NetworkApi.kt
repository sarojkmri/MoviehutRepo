package com.dev.moviehut.data.remote

import com.dev.moviehut.data.model.MovieData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET(EndPoints.TOP_RATED_MOVIES)
    fun getTopRatedMovies(@Query("api_key") apiKey: String): Single<MovieData>

    @GET(EndPoints.POPULAR_MOVIES)
    fun getMostPopularMovies(@Query("api_key") apiKey: String): Single<MovieData>

    @GET(EndPoints.NOW_PLAYING_MOVIES)
    fun getNowPlayingMovies(@Query("api_key") apiKey: String): Single<MovieData>

    @GET(EndPoints.LATEST_MOVIES)
    fun getLatestMovies(@Query("api_key") apiKey: String): Single<MovieData>
}