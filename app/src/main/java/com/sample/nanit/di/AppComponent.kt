package com.sample.nanit.di

import android.content.Context
import com.sample.nanit.view.BirthdayFragment
import com.sample.nanit.view.DetailsFragment
import com.sample.nanit.view.PhotoPickerFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Main component of the app, created and persisted in the Application class.
 *
 */
@Singleton
@Component(modules = [AppModule::class, AppViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: BirthdayFragment)
    fun inject(fragment: DetailsFragment)
    fun inject(fragment: PhotoPickerFragment)
}