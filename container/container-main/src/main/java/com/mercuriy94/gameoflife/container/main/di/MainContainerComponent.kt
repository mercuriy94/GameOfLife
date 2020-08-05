package com.mercuriy94.gameoflife.container.main.di

import com.mercuriy94.gameoflife.container.main.presentation.MainActivity
import com.mercuriy94.gameoflife.core.di.scope.PerContainer
import com.mercuriy94.gameoflife.core.di.util.ComponentDependencies
import com.mercuriy94.gameoflife.core.di.util.findComponentDependencies
import com.mercuriy94.gameoflife.core.ui.di.module.ViewModelModule
import com.mercuriy94.gameoflife.flow.main.di.MainFlowDeps
import dagger.BindsInstance
import dagger.Component


/**
 * @author Nikita Marsyukov
 */
@PerContainer
@Component(
    dependencies = [MainContainerDeps::class],
    modules = [
        MainContainerModule::class,
        MainContainerRouterModule::class
    ]
)
interface MainContainerComponent : MainFlowDeps {

    fun inject(target: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bind(target: MainActivity): Builder

        fun deps(deps: MainContainerDeps): Builder

        fun build(): MainContainerComponent
    }

    companion object {

        fun inject(target: MainActivity) {
            DaggerMainContainerComponent.builder()
                .deps(target.findComponentDependencies())
                .bind(target)
                .build()
                .inject(target)
        }
    }
}

interface MainContainerDeps : ComponentDependencies
