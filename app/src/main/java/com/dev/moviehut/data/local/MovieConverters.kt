package com.dev.moviehut.data.local

import androidx.room.TypeConverter
import com.dev.moviehut.utils.MovieListUtil
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class MovieConverters {
    private val SEPARATOR = "-"

    //converting json string to gson of type Movie class
    @TypeConverter
    fun restoreList(listOfInt: String?): List<Int?>? {
        return Gson().fromJson<List<Int?>>(
            listOfInt,
            object : TypeToken<List<Int?>?>() {}.type
        )
    }

    //converting list object to json string
    @TypeConverter
    fun saveList(listOfInt: List<Int?>?): String? {
        return Gson().toJson(listOfInt)
    }

    //GET_TOP_RATED-GET_POPULAR-GET_NOW_PLAYING-GET_LATEST
    @TypeConverter
    fun movieListToString(movieList: MutableSet<MovieListUtil.MovieList>): String? =
        movieList.map { it.ordinal }.joinToString(separator = SEPARATOR)

    @TypeConverter
    fun stringToMovieList(movieList: String): MutableSet<MovieListUtil.MovieList> =
        movieList.split(SEPARATOR).map { MovieListUtil.movieListFromInt(it.toInt()) }.toMutableSet()
}