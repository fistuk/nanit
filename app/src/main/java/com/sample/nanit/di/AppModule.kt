package com.sample.nanit.di

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.nanit.view.BirthdayViewModel
import com.sample.nanit.view.DetailsViewModel
import com.sample.nanit.view.PhotoPickerViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class AppModule {

}

@Module
internal abstract class AppViewModelModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDefaultArgs(): Bundle? {
            return null
        }
    }

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotoPickerViewModel::class)
    internal abstract fun bindPhotoPickerViewModel(viewModel: PhotoPickerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BirthdayViewModel::class)
    internal abstract fun bindBirthdayViewModel(viewModel: BirthdayViewModel): ViewModel
}
