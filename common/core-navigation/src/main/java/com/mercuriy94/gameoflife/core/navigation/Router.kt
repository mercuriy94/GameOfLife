package com.mercuriy94.gameoflife.core.navigation

import androidx.navigation.NavDirections
import javax.inject.Inject

/**
 * @author Nikita Marsyukov
 */
interface Router {

    fun navigateTo(destinationId: Int)

    fun navigateTo(navDirections: NavDirections)

    fun navigate(command: Command)

    fun back()

}

interface BackRouter {

    fun back()

}

class RouterImpl @Inject constructor(private val navigator: Navigator) : Router {

    override fun navigateTo(destinationId: Int) {
        navigator.applyCommand(NavigateCommand { it.navigate(destinationId) })
    }

    override fun navigateTo(navDirections: NavDirections) {
        navigator.applyCommand(NavigateCommand { it.navigate(navDirections) })
    }

    override fun navigate(command: Command) {
        navigator.applyCommand(command)
    }

    override fun back() {
        navigator.applyCommand(BackCommand)
    }

}
