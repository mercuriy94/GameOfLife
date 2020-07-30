package com.mercuriy94.gameoflife.di.app

import android.content.Context
import com.mercuriy94.gameoflife.GameOfLifeApp
import com.mercuriy94.gameoflife.di.PerApplication
import dagger.Binds
import dagger.Module

/**
 * @author Nikita Marsyukov
 */
@Module
abstract class GameOfLifeAppModule() {

    @PerApplication
    @Binds
    abstract fun provideContext(app: GameOfLifeApp): Context

}