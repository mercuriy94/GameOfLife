package ru.inmoso.core.ui.extensions

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

fun Fragment.safeOpenSettings(builderAction: (Intent.() -> Unit)? = null): Boolean {
    val intent = Intent(Settings.ACTION_SETTINGS)
    if (builderAction != null) intent.builderAction()
    return safeStartActivity(intent)
}

fun Fragment.safeOpenNetworkSettings(builderAction: (Intent.() -> Unit)? = null): Boolean {
    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
    if (builderAction != null) intent.builderAction()
    return safeStartActivity(intent)
}
