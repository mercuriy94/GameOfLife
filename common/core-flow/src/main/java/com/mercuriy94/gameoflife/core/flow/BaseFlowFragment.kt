package com.mercuriy94.gameoflife.core.flow

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.mercuriy94.gameoflife.core.navigation.BackRouter
import com.mercuriy94.gameoflife.core.navigation.NavigationEvents
import com.mercuriy94.gameoflife.core.navigation.NavigationObserver
import com.mercuriy94.gameoflife.core.ui.base.NavigationFragment
import com.mercuriy94.gameoflife.core.ui.base.NavigationHost
import javax.inject.Inject

/**
 * @author Nikita Marsyukov
 */
abstract class BaseFlowFragment<VMS, VB : ViewBinding>(@IdRes private val navHostId: Int) :
    NavigationFragment<VMS, VB>(),
    NavigationHost where VMS : ViewModel,
                         VMS : BackRouter,
                         VMS : NavigationEvents {

    private lateinit var _flowNavController: NavController

    private var isTop: Boolean = true

    @Inject
    lateinit var navigationObserver: NavigationObserver

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (nextAnim == 0) return super.onCreateAnimation(transit, enter, nextAnim)
        val nextAnimation: Animation = AnimationUtils.loadAnimation(context, nextAnim)
        nextAnimation.setAnimationListener(object : Animation.AnimationListener {
            private var mOldTranslationZ = 0f

            override fun onAnimationStart(animation: Animation?) {
                mOldTranslationZ = ViewCompat.getTranslationZ(viewBinding.root)
                viewBinding.root.setBackgroundResource(R.color.background_color)
                ViewCompat.setTranslationZ(viewBinding.root, 100f)
            }

            override fun onAnimationEnd(animation: Animation) {
                if (view != null && enter) {
                    ViewCompat.setTranslationZ(viewBinding.root, mOldTranslationZ)
                    viewBinding.root.postDelayed({
                        viewBinding.root.background = null
                    }, resources.getInteger(R.integer.animation_duration_short).toLong())
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        return nextAnimation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _flowNavController = (childFragmentManager.findFragmentById(navHostId)
                as NavHostFragment).navController
        viewModel.events.observe(viewLifecycleOwner, navigationObserver)
        isTop = parentNavigationHost?.isTopDestination() ?: true
    }

    override fun getNavController(): NavController = _flowNavController

    override fun isTopDestination(): Boolean {
        val config = AppBarConfiguration.Builder(_flowNavController.graph).build()
        return config.topLevelDestinations.contains(_flowNavController.currentDestination?.id)
    }

    protected fun isTop(): Boolean = isTop

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        // Если текущий Destination является стартовым в родительском navController-е,
        // то кнопка "Вверх" для стартового Destination который находится в текущем navController
        // не должна показываться. В противном случае если текущий Destination не является
        // стартовым в родительском navController-е, то кнопка "Вверх" для стартового Destination
        // который находится в текущем navController должна показываться.
        val config = when {
            isTop -> AppBarConfiguration.Builder(_flowNavController.graph)
            else -> {
                toolbar.navigationIcon = DrawerArrowDrawable(context).apply { progress = 1f }
                AppBarConfiguration.Builder(emptySet())
            }
        }.setFallbackOnNavigateUpListener {
            viewModel.back()
            true
        }.build()

        NavigationUI.setupWithNavController(toolbar, _flowNavController, config)

    }

}
