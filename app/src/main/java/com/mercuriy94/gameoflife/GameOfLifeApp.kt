package com.mercuriy94.gameoflife

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.mercuriy94.gameoflife.core.di.util.ComponentDependenciesProvider
import com.mercuriy94.gameoflife.core.di.util.HasComponentDependencies
import com.mercuriy94.gameoflife.di.InjectorComponent
import javax.inject.Inject


/**
 * @author Nikita Marsyukov
 */
class GameOfLifeApp : Application(), HasComponentDependencies{

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        InjectorComponent.inject(this)
    }

}
