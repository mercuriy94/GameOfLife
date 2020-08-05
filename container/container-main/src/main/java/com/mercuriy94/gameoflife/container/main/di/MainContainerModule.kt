package com.mercuriy94.gameoflife.container.main.di

import androidx.lifecycle.ViewModel
import com.mercuriy94.gameoflife.container.main.presentation.MainContainerViewModel
import com.mercuriy94.gameoflife.core.di.scope.PerContainer
import com.mercuriy94.gameoflife.core.di.util.ComponentDependencies
import com.mercuriy94.gameoflife.core.di.util.ComponentDependenciesKey
import com.mercuriy94.gameoflife.core.ui.di.ViewModelKey
import com.mercuriy94.gameoflife.core.ui.di.module.ViewModelModule
import com.mercuriy94.gameoflife.flow.main.di.MainFlowDeps
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * @author Nikita Marsyukov
 */
@Module(includes = [ViewModelModule::class])
abstract class MainContainerModule {

    @ViewModelKey(MainContainerViewModel::class)
    @IntoMap
    @PerContainer
    @Binds
    abstract fun bindMainContainerViewModel(viewModel: MainContainerViewModel): ViewModel

    @IntoMap
    @ComponentDependenciesKey(MainFlowDeps::class)
    @PerContainer
    @Binds
    abstract fun bindMainFlowDeps(component: MainContainerComponent): ComponentDependencies

}