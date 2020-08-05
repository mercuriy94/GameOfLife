package com.mercuriy94.gameoflife.feature.life.di

import com.mercuriy94.gameoflife.core.di.Injector
import com.mercuriy94.gameoflife.core.di.scope.PerFeature
import com.mercuriy94.gameoflife.core.di.util.ComponentDependencies
import com.mercuriy94.gameoflife.core.di.util.findComponentDependencies
import com.mercuriy94.gameoflife.domain.interactor.api.DomainApi
import com.mercuriy94.gameoflife.feature.life.presentation.LifeFragment
import dagger.BindsInstance
import dagger.Component

/**
 * @author Nikita Marsyukov
 */
@PerFeature
@Component(
    dependencies = [LifeFeatureDeps::class],
    modules = [LifeFeatureModule::class]
)
interface LifeFeatureComponent : Injector<LifeFragment> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bind(target: LifeFragment): Builder

        fun deps(deps: LifeFeatureDeps): Builder

        fun build(): LifeFeatureComponent
    }

    companion object {
        fun inject(fragment: LifeFragment) {
            DaggerLifeFeatureComponent
                .builder()
                .deps(fragment.findComponentDependencies())
                .bind(fragment)
                .build()
                .inject(fragment)
        }
    }
}

interface LifeFeatureDeps : DomainApi, ComponentDependencies {


}
