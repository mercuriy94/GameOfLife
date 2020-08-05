package com.mercuriy94.gameoflife.core.flow

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mercuriy94.gameoflife.core.navigation.FragmentSupportNavigationObserver
import com.mercuriy94.gameoflife.core.ui.base.NavigationHost

/**
 * @author Nikita Marsyukov
 */
class FlowNavigationObserver<F>(
    activity: Activity,
    private val fragment: F
) : FragmentSupportNavigationObserver(
    activity = activity,
    fragment = fragment,
    delegateNavigationCommand = { navigationCommand ->
        navigationCommand(fragment.navController)
        true
    },
    delegateBack = {
        when {
            fragment.navController.navigateUp() -> true
            else -> fragment.findNavController().navigateUp()
        }
    }

) where F : Fragment, F : NavigationHost
