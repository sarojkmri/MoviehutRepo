package com.dev.moviehut.data.local

import androidx.room.TypeConverter
import com.dev.moviehut.utils.MovieListUtil
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class MovieConverters {
    private val SEPARATOR = "-"

    @TypeConverter
    fun restoreList(listOfInt: String?): List<Int?>? {
        return Gson().fromJson<List<Int?>>(
            listOfInt,
            object : TypeToken<List<Int?>?>() {}.type
        )
    }

    @TypeConverter
    fun saveList(listOfInt: List<Int?>?): String? {
        return Gson().toJson(listOfInt)
    }

    @TypeConverter
    fun movieListToString(movieList: MutableSet<MovieListUtil.MovieList>): String? =
        movieList.map { it.ordinal }.joinToString(separator = SEPARATOR)

    @TypeConverter
    fun stringToMovieList(movieList: String): MutableSet<MovieListUtil.MovieList> =
        movieList.split(SEPARATOR).map { MovieListUtil.movieListFromInt(it.toInt()) }.toMutableSet()
}