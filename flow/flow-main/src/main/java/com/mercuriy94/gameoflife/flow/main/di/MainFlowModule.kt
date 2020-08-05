package com.mercuriy94.gameoflife.flow.main.di

import androidx.lifecycle.ViewModel
import com.mercuriy94.gameoflife.core.di.scope.PerFlow
import com.mercuriy94.gameoflife.core.ui.di.ViewModelKey
import com.mercuriy94.gameoflife.core.ui.di.module.ViewModelModule
import com.mercuriy94.gameoflife.flow.main.presentation.MainFlowViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * @author Nikita Marsyukov
 */
@Module(includes = [ViewModelModule::class])
abstract class MainFlowModule {

    @ViewModelKey(MainFlowViewModel::class)
    @IntoMap
    @PerFlow
    @Binds
    abstract fun bindMainFlowViewModel(viewModel: MainFlowViewModel): ViewModel

}
