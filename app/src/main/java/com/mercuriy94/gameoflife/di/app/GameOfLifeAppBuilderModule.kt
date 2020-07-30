package com.mercuriy94.gameoflife.di.app

import com.mercuriy94.gameoflife.di.PerActivity
import com.mercuriy94.gameoflife.presentation.feature.main.MainActivity
import com.mercuriy94.gameoflife.presentation.feature.main.di.MainViewModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Nikita Marsyukov
 */
@Module
interface GameOfLifeAppBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    fun provideMainActivityFactory(): MainActivity

}