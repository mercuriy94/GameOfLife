package com.mercuriy94.gameoflife.presentation.feature.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mercuriy94.gameoflife.di.PerActivity
import com.mercuriy94.gameoflife.di.ViewModelKey
import com.mercuriy94.gameoflife.di.ViewModelModule
import com.mercuriy94.gameoflife.presentation.feature.main.MainActivity
import com.mercuriy94.gameoflife.presentation.feature.main.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * @author Nikita Marsyukov
 */
@Module()
object MainViewModule {

    @ViewModelKey(MainViewModel::class)
    @IntoMap
    @PerActivity
    @Provides
    fun provideViewModel(): ViewModel = MainViewModel()

    @PerActivity
    @Provides
    fun provideMainViewModel(
        activity: MainActivity,
        factory: ViewModelModule.ViewModelModelFactory
    ): MainViewModel =
        ViewModelProvider(activity, factory)
            .get(MainViewModel::class.java)
}