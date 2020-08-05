package com.mercuriy94.gameoflife.flow.main.di

import com.mercuriy94.gameoflife.core.di.scope.PerFlow
import com.mercuriy94.gameoflife.core.di.util.ComponentDependencies
import com.mercuriy94.gameoflife.core.di.util.ComponentDependenciesKey
import com.mercuriy94.gameoflife.feature.life.di.LifeFeatureDeps
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Nikita Marsyukov
 */
@Module
abstract class MainFlowBuildModule {

    @IntoMap
    @ComponentDependenciesKey(LifeFeatureDeps::class)
    @PerFlow
    @Binds
    abstract fun bindLifeFeatureDeps(impl: MainFlowComponent): ComponentDependencies

}
