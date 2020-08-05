package com.mercuriy94.gameoflife.di

import android.content.Context
import com.mercuriy94.gameoflife.GameOfLifeApp
import com.mercuriy94.gameoflife.core.di.scope.PerApplication
import dagger.Module
import dagger.Provides


/**
 * @author Nikita Marsyukov
 */
@Module
object InjectorModule {

    @Provides
    @PerApplication
    fun provideContext(app: GameOfLifeApp): Context = app.applicationContext

}
