package com.mercuriy94.gameoflife.presentation.feature.main

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.mercuriy94.gameoflife.databinding.ActivityMainBinding
import com.mercuriy94.gameoflife.presentation.base.BaseActivity
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
            viewBinding.gameOfLifeView.fill()
        }

        viewBinding.btnStart.setOnClickListener {
            viewBinding.gameOfLifeView.start()
        }
    }
}