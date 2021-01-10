package com.dev.moviehut.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.moviehut.data.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(MovieConverters::class)
abstract class DatabaseService : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}