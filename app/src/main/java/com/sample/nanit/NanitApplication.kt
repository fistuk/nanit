package com.sample.nanit

import android.app.Application
import com.sample.nanit.di.AppComponent
import com.sample.nanit.di.DaggerAppComponent

class NanitApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeAppComponent()
    }

    fun initializeAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}