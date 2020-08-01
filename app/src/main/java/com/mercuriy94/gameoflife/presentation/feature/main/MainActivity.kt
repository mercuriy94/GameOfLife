package com.mercuriy94.gameoflife.presentation.feature.main

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.badoo.mvicore.ModelWatcher
import com.badoo.mvicore.modelWatcher
import com.mercuriy94.gameoflife.databinding.ActivityMainBinding
import com.mercuriy94.gameoflife.presentation.base.BaseActivity
import ru.inmoso.core.ui.events.EventObserver
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)

        viewBinding.btnFill.setOnClickListener {
            viewModel.uiEvent(FillFieldUiEvent)
        }

        viewBinding.btnStart.setOnClickListener {
            viewModel.uiEvent(StartUiEvent)
        }

        viewBinding.btnStep.setOnClickListener {
            viewModel.uiEvent(StepUiEvent)
        }

        viewBinding.btnPause.setOnClickListener {
            viewModel.uiEvent(PauseUiEvent)
        }

        val watcher = modelWatcher<MainViewState> {

            MainViewState::lifeField {
                viewBinding.gameOfLifeView.fill(it)
            }

            MainViewState::isStartVisible {
                viewBinding.btnStart.isVisible = it
            }

            MainViewState::isPauseVisible {
                viewBinding.btnPause.isVisible = it
            }

        }

        viewModel.viewState.observe(this, Observer { state ->
            watcher.invoke(state)
        })

    }
}