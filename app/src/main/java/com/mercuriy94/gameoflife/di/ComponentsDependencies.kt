package com.mercuriy94.gameoflife.di

import com.mercuriy94.gameoflife.container.main.di.MainContainerDeps
import com.mercuriy94.gameoflife.core.di.util.ComponentDependencies
import com.mercuriy94.gameoflife.core.di.util.ComponentDependenciesKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * @author Nikita Marsyukov
 */
@Module
interface ComponentsDependencies {

    @Binds
    @IntoMap
    @ComponentDependenciesKey(MainContainerDeps::class)
    fun bindMainContainerDeps(component: InjectorComponent): ComponentDependencies

}
