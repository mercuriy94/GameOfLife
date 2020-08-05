package com.mercuriy94.gameoflife.feature.life.di

import androidx.lifecycle.ViewModel
import com.mercuriy94.gameoflife.core.di.scope.PerFeature
import com.mercuriy94.gameoflife.core.di.scope.PerFlow
import com.mercuriy94.gameoflife.core.ui.di.ViewModelKey
import com.mercuriy94.gameoflife.core.ui.di.module.ViewModelModule
import com.mercuriy94.gameoflife.feature.life.presentation.LifeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Nikita Marsyukov
 */
@Module(includes = [ViewModelModule::class])
abstract class LifeFeatureModule {

    @ViewModelKey(LifeViewModel::class)
    @IntoMap
    @PerFeature
    @Binds
    abstract fun bindLifeViewModel(viewModel: LifeViewModel): ViewModel

}
