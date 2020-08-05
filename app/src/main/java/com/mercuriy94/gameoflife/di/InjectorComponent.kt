package com.mercuriy94.gameoflife.di

import com.mercuriy94.gameoflife.GameOfLifeApp
import com.mercuriy94.gameoflife.container.main.di.MainContainerDeps
import com.mercuriy94.gameoflife.core.di.scope.PerApplication
import com.mercuriy94.gameoflife.domain.interactor.api.DomainApi
import dagger.BindsInstance
import dagger.Component
import ru.inmoso.fotoprint.di.InjectorModuleBuild


/**
 * @author Nikita Marsyukov
 */
@PerApplication
@Component(
    modules = [
        ComponentsDependencies::class,
        InjectorModuleBuild::class,
        InjectorModule::class
    ]
)
interface InjectorComponent : MainContainerDeps, DomainApi {

    fun inject(app: GameOfLifeApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bind(app: GameOfLifeApp): Builder

        fun build(): InjectorComponent
    }

    companion object {
        fun inject(app: GameOfLifeApp) {

            DaggerInjectorComponent.builder()
                .bind(app)
                .build()
                .inject(app)
        }
    }
}
