package com.mercuriy94.gameoflife.core.navigation

import android.content.Context
import android.content.Intent

/**
 * @author Nikita Marsyukov
 */
abstract class IntentProducer {
    protected open val tag: String? = null
    abstract fun getIntent(context: Context): Intent
}
