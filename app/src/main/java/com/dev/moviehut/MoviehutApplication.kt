package com.dev.moviehut

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import com.dev.moviehut.di.ApplicationComponent
import com.dev.moviehut.di.ApplicationModule
import com.dev.moviehut.di.DaggerApplicationComponent

class MoviehutApplication : Application() {
    private lateinit var applicationComponent: ApplicationComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);

    }

    override fun onCreate() {
        super.onCreate()
        application = this
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule())
            .build()
    }

    companion object {
        lateinit var application: Application
    }

}