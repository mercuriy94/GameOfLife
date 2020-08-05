package com.mercuriy94.gameoflife.container.main.di

import com.mercuriy94.gameoflife.container.main.navigation.MainFlowRouterImpl
import com.mercuriy94.gameoflife.core.di.scope.PerContainer
import com.mercuriy94.gameoflife.flow.main.navigation.MainFlowRouter
import dagger.Binds
import dagger.Module

/**
 * @author Nikita Marsyukov
 */
@Module
abstract class MainContainerRouterModule {

    @PerContainer
    @Binds
    abstract fun bindMainFlowRouter(impl: MainFlowRouterImpl): MainFlowRouter

}
