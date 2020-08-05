package com.mercuriy94.gameoflife.core.ui.extensions

import android.content.Intent
import android.provider.Settings
import androidx.fragment.app.Fragment

fun Fragment.safeStartActivity(intent: Intent): Boolean {
    val activity = activity ?: return false
    if (intent.resolveActivity(activity.packageManager) != null) {
        startActivity(intent)
        return true
    }

    return false
}
