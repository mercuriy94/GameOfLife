package com.mercuriy94.gameoflife.core.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mercuriy94.gameoflife.core.ui.events.LiveEvent
import com.mercuriy94.gameoflife.core.ui.events.handle
import com.mercuriy94.gameoflife.core.ui.extensions.safeStartActivity


/**
 * Класс преназначен для наблюдения на событиями навигации для дальнейшей конкретной реализации
 *
 * @author Nikita Marsyukov
 * */
abstract class NavigationObserver : Observer<LiveEvent<Command>>

/**
 *  Реализация [NavigationObserver] с поддержкой фрагмента
 *  Для кастомной логики можно использовать делегаты
 * */
open class FragmentSupportNavigationObserver @JvmOverloads constructor(
    private val activity: Activity,
    private val fragment: Fragment,
    private val delegateNavigationCommand: ((navigationCommand: NavigationCommand) -> Boolean)? = null,
    private val delegateStartActivity: ((intentProducer: IntentProducer) -> Boolean)? = null,
    private val delegateStartService: ((intentProducer: IntentProducer) -> Boolean)? = null,
    private val delegateBack: (() -> Boolean)? = null
) : NavigationObserver() {

    override fun onChanged(t: LiveEvent<Command>?) {
        t?.handle(::handle)
    }

    protected open fun handle(command: Command) {
        when (command) {
            is NavigateCommand -> navigate(command.navigationCommand)
            is StartActivityCommand -> startActivity(command.intentProducer)
            is StartServiceCommand -> startService(command.intentProducer)
            is BackCommand -> back()
        }
    }

    private fun navigate(navigationCommand: NavigationCommand) {
        if (delegateNavigationCommand?.invoke(navigationCommand) != true) {
            navigationCommand(fragment.findNavController())
        }
    }

    private fun startActivity(intentProducer: IntentProducer) {
        if (delegateStartActivity?.invoke(intentProducer) != true) {
            fragment.safeStartActivity(intentProducer.getIntent(activity))
        }
    }

    private fun startService(intentProducer: IntentProducer) {
        if (delegateStartService?.invoke(intentProducer) != true) {
            activity.startService(intentProducer.getIntent(activity))
        }
    }

    private fun back() {
        if (delegateBack?.invoke() != true) {
            navigate { it.navigateUp() }
        }
    }

}
