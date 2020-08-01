package com.mercuriy94.gameoflife

import com.mercuriy94.gameoflife.di.app.DaggerGameOfLifeAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * @author Nikita Marsyukov
 */
class GameOfLifeApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerGameOfLifeAppComponent
            .factory()
            .create(this)

}
