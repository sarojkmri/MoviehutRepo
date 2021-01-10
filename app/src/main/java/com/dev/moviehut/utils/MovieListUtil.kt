package com.dev.moviehut.utils

import android.content.Context
import com.dev.moviehut.R

class MovieListUtil {
    enum class MovieList(val value: Int) {
        GET_TOP_RATED(0),
        GET_POPULAR(1),
        GET_NOW_PLAYING(2),
        GET_LATEST(3),
    }

    companion object {
        fun getMovieList(context: Context): List<String> {
            return listOf(
                context.getString(R.string.get_top),
                context.getString(R.string.get_popular),
                context.getString(R.string.get_playing),
                context.getString(R.string.get_latest)
            )
        }

        private val map = MovieList.values().associateBy(MovieList::value)
        fun movieListFromInt(type: Int) = map[type] ?: error("")
    }
}