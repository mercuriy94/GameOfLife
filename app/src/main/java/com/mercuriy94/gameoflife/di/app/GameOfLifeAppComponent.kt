package com.mercuriy94.gameoflife.di.app

import com.mercuriy94.gameoflife.GameOfLifeApp
import com.mercuriy94.gameoflife.di.PerApplication
import com.mercuriy94.gameoflife.di.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * @author Nikita Marsyukov
 */
@PerApplication
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        GameOfLifeAppModule::class,
        GameOfLifeAppBuilderModule::class,
        ViewModelModule::class
    ]
)
interface GameOfLifeAppComponent : AndroidInjector<GameOfLifeApp> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<GameOfLifeApp>
}