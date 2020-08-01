@file:JvmName("ActivityExt")

package ru.inmoso.core.ui.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService

fun Activity.showKeyboard() {
    getSystemService<InputMethodManager>()
        ?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Activity.hideKeyboard(view: View?) {
    getSystemService<InputMethodManager>()?.apply {
        hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

fun Activity.safeStartActivity(intent: Intent): Boolean {

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }

    return false
}

fun Activity.safeOpenSettings(builderAction: Intent.() -> Unit): Boolean {
    val intent = Intent(Settings.ACTION_SETTINGS)
    intent.builderAction()
    return safeStartActivity(intent)
}

fun Activity.safeOpenNetworkSettings(builderAction: Intent.() -> Unit): Boolean {
    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
    intent.builderAction()
    return safeStartActivity(intent)
}

/**
 * Безопасное открытие настроек уведомления приложения
 * @return true если в системе нашлось приложение которые может обработать [Intent]]
 * */
fun Activity.safeOpenNotificationSettings(): Boolean =
    safeStartActivity(
        Intent().apply {

            when {
                Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1 -> {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    putExtra("app_package", packageName)
                    putExtra("app_uid", applicationInfo.uid)
                }
                else -> {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    addCategory(Intent.CATEGORY_DEFAULT)
                    data = Uri.parse("package:$packageName")
                }
            }
        }
    )

/**
 * Execute [f] only if the current Android SDK version is [version] or older.
 * Do nothing otherwise.
 */
inline fun doBeforeSdk(version: Int, f: () -> Unit) {
    if (Build.VERSION.SDK_INT <= version) f()
}

/**
 * Execute [doIfTrue] only if the current Android SDK version is [version] or newer.
 * Do nothing otherwise.
 */
inline fun doFromSdk(version: Int, doIfTrue: () -> Unit, doElse: () -> Unit = {}) {
    if (Build.VERSION.SDK_INT >= version) doIfTrue() else doElse()
}

/**
 * Execute [f] only if the current Android SDK version is [version].
 * Do nothing otherwise.
 */
inline fun doIfSdk(version: Int, f: () -> Unit) {
    if (Build.VERSION.SDK_INT == version) f()
}

fun Activity.hideSystemUI() {
    // Enables regular immersive mode.
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}

// Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
fun Activity.showSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}