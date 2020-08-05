package com.mercuriy94.gameoflife.core.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHost
import androidx.viewbinding.ViewBinding

/**
 * @author Nikita Marsyukov
 */
interface NavigationHost : NavHost {

    fun isTopDestination(): Boolean

    fun registerToolbarWithNavigation(toolbar: Toolbar)

}

abstract class NavigationFragment<VM : ViewModel, VB : ViewBinding>() : BaseFragment<VM, VB>() {

    protected var parentNavigationHost: NavigationHost? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        var current: Fragment? = parentFragment

        while (current !is NavigationHost?) {
            current = current?.parentFragment
        }

        parentNavigationHost = current ?: when (activity) {
            is NavigationHost -> activity as NavigationHost
            else -> throw IllegalStateException("Can not find suitable NavigationHost provider for $this")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val host = parentNavigationHost ?: return
        val mainToolbar: Toolbar = getToolbar() ?: return
        host.registerToolbarWithNavigation(mainToolbar)
    }

    override fun onDetach() {
        super.onDetach()
        parentNavigationHost = null
    }

    open fun getToolbar(): Toolbar? = null

}
