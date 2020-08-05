package com.mercuriy94.gameoflive.core.mvi

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.badoo.mvicore.ModelWatcher
import com.badoo.mvicore.modelWatcher

import com.mercuriy94.gameoflife.core.ui.base.NavigationFragment

/**
 * @author Nikita Marsyukov
 */
abstract class BaseMviFeatureNavigationFragment<State : Any, VM, VB : ViewBinding> :
    NavigationFragment<VM, VB>() where VM : ViewModel, VM : Store<*, State, *, *, *> {

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {

        if (nextAnim == 0) {
            return super.onCreateAnimation(transit, enter, nextAnim)
        }

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
        var isWasTransitionAnim = false

        if (parentNavigationHost?.isTopDestination() != true) {
            // View is created so postpone the transition
            postponeEnterTransition()
        } else {
            isWasTransitionAnim = true
        }

        val watcher: ModelWatcher<State> = modelWatcher(watchState())

        viewModel.viewState.observe(viewLifecycleOwner, Observer { newState ->

            watcher.invoke(newState)

            if (!isWasTransitionAnim) {
                // Data is loaded so lets wait for our parent to be drawn
                (view.parent as? ViewGroup)?.doOnPreDraw {
                    if (!isWasTransitionAnim) {
                        // Parent has been drawn. Start transitioning!
                        startPostponedEnterTransition()
                        isWasTransitionAnim = true
                    }
                }
            }

        })

    }

    abstract fun watchState(): ModelWatcher.Builder<State>.() -> Unit

}
