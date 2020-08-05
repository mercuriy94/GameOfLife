package com.mercuriy94.gameoflife.core.navigation.di

import androidx.lifecycle.ViewModel
import com.mercuriy94.gameoflife.core.di.scope.PerFlow
import com.mercuriy94.gameoflife.core.navigation.NavigationViewModel
import com.mercuriy94.gameoflife.core.navigation.NavigatorHolderViewModel
import com.mercuriy94.gameoflife.core.navigation.Router
import com.mercuriy94.gameoflife.core.navigation.RouterImpl
import com.mercuriy94.gameoflife.core.ui.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Данный класс предназначен для уменьшения бойлерплейта,
 * а именно прекратить дублирование кода для сохранения вьюмоделей в мапу:
 * [NavigationViewModel] - предоставляет события наивгации;
 * [NavigatorHolderViewModel] - помогает сохранить [Navigator]
 * при изменении конфигурации устрйоства (например при поворотах экрана);
 * И предоставляет базовую реализацию роутера [RouterImpl]
 *
 * Дагер компоненты которые использует данный модуль,
 * должны создержать в своих графах зависимостей [Navigator],
 * который передается в [NavigationViewModel].
 *
 * @author Nikita Marsyukov
 */
@Module
abstract class DefaultFlowRouterModule {

    @PerFlow
    @Binds
    abstract fun bindRouter(router: RouterImpl): Router

    @ViewModelKey(NavigationViewModel::class)
    @IntoMap
    @PerFlow
    @Binds
    abstract fun bindNavigationEventsViewModel(impl: NavigationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(NavigatorHolderViewModel::class)
    @PerFlow
    @Binds
    abstract fun bindNavigatorHolderViewModel(impl: NavigatorHolderViewModel): ViewModel

}
