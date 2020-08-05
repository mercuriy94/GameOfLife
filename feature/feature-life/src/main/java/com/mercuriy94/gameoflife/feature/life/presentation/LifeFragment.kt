package com.mercuriy94.gameoflife.feature.life.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.badoo.mvicore.ModelWatcher
import com.mercuriy94.gameoflife.feature.life.databinding.LifeFragmentLayoutBinding
import com.mercuriy94.gameoflife.feature.life.di.LifeFeatureComponent
import com.mercuriy94.gameoflive.core.mvi.BaseMviFeatureNavigationFragment
import dev.chrisbanes.insetter.applySystemGestureInsetsToPadding
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin

/**
 * @author Nikita Marsyukov
 */
class LifeFragment :
    BaseMviFeatureNavigationFragment<LifeViewState, LifeViewModel, LifeFragmentLayoutBinding>() {

    override val viewModelClass: Class<LifeViewModel> = LifeViewModel::class.java

    override fun inject() = LifeFeatureComponent.inject(this)

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LifeFragmentLayoutBinding? = LifeFragmentLayoutBinding.inflate(inflater)

    override fun getToolbar(): Toolbar? = viewBinding.toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {

            btnFill.setOnClickListener {
                viewModel.uiEvent(FillFieldUiEvent)
            }

            btnStart.setOnClickListener {
                viewModel.uiEvent(StartUiEvent)
            }

            btnStep.setOnClickListener {
                viewModel.uiEvent(StepUiEvent)
            }

            btnPause.setOnClickListener {
                viewModel.uiEvent(PauseUiEvent)
            }

        }

    }

    override fun watchState(): ModelWatcher.Builder<LifeViewState>.() -> Unit = {

        LifeViewState::lifeField {
            viewBinding.gameOfLifeView.fill(it)
        }

        LifeViewState::isStartVisible {
            viewBinding.btnStart.isVisible = it
        }

        LifeViewState::isPauseVisible {
            viewBinding.btnPause.isVisible = it
        }
    }

    override fun setupInsets() {
        super.setupInsets()
        viewBinding.apply {

            root.applySystemWindowInsetsToMargin(
                left = true,
                right = true,
                bottom = true
            )

            appBar.applySystemGestureInsetsToPadding(top = true)
        }
    }

}
