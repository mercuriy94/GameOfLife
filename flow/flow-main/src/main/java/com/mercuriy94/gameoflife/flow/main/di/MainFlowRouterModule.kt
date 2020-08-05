package com.mercuriy94.gameoflife.flow.main.di

import androidx.lifecycle.ViewModelProvider
import com.mercuriy94.gameoflife.core.di.scope.PerFlow
import com.mercuriy94.gameoflife.core.flow.FlowNavigationObserver
import com.mercuriy94.gameoflife.core.navigation.BackRouter
import com.mercuriy94.gameoflife.core.navigation.NavigationObserver
import com.mercuriy94.gameoflife.core.navigation.Navigator
import com.mercuriy94.gameoflife.core.navigation.NavigatorHolderViewModel
import com.mercuriy94.gameoflife.core.navigation.di.DefaultFlowRouterModule
import com.mercuriy94.gameoflife.flow.main.navigation.MainFlowRouter
import com.mercuriy94.gameoflife.flow.main.presentation.MainFlowFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * @author Nikita Marsyukov
 */
@Module(includes = [DefaultFlowRouterModule::class])
abstract class MainFlowRouterModule {

    @PerFlow
    @Binds
    abstract fun bindBackRouter(impl: MainFlowRouter): BackRouter

    companion object {

        @PerFlow
        @Provides
        fun provideNavigationObserver(flowFragment: MainFlowFragment): NavigationObserver =
            FlowNavigationObserver(flowFragment.requireActivity(), flowFragment)

        @PerFlow
        @Provides
        fun provideNavigator(
            factory: ViewModelProvider.Factory,
            fragment: MainFlowFragment
        ): Navigator =
            ViewModelProvider(fragment, factory)
                .get(NavigatorHolderViewModel::class.java)
                .navigator

    }

}