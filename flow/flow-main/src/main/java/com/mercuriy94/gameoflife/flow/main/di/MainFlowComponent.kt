package com.mercuriy94.gameoflife.flow.main.di

import com.mercuriy94.gameoflife.core.di.Injector
import com.mercuriy94.gameoflife.core.di.scope.PerFlow
import com.mercuriy94.gameoflife.core.di.util.ComponentDependencies
import com.mercuriy94.gameoflife.core.di.util.findComponentDependencies
import com.mercuriy94.gameoflife.domain.interactor.api.DomainApi
import com.mercuriy94.gameoflife.feature.life.di.LifeFeatureDeps
import com.mercuriy94.gameoflife.flow.main.navigation.MainFlowRouter
import com.mercuriy94.gameoflife.flow.main.presentation.MainFlowFragment
import dagger.BindsInstance
import dagger.Component

/**
 * @author Nikita Marsyukov
 */
@PerFlow
@Component(
    dependencies = [MainFlowDeps::class],
    modules = [
        MainFlowModule::class,
        MainFlowBuildModule::class,
        MainFlowRouterModule::class
    ]
)
interface MainFlowComponent : Injector<MainFlowFragment>, LifeFeatureDeps {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bind(target: MainFlowFragment): Builder

        fun deps(deps: MainFlowDeps): Builder

        fun build(): MainFlowComponent
    }

    companion object {
        fun inject(fragment: MainFlowFragment) {
            DaggerMainFlowComponent
                .builder()
                .deps(fragment.findComponentDependencies())
                .bind(fragment)
                .build()
                .inject(fragment)
        }
    }
}

interface MainFlowDeps : DomainApi, ComponentDependencies {

    fun mainFlowRouter(): MainFlowRouter

}
