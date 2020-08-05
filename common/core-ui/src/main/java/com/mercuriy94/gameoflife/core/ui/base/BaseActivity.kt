package com.mercuriy94.gameoflife.core.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import timber.log.Timber
import javax.inject.Inject

/**
 * Абстрактный базовый класс активити [androidx.appcompat.app.AppCompatActivity],
 * где автоматически вызывается метод инъекции зависимостей,
 * который должны наслденики данного класса
 *
 * @author Nikita Marsyukov
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding>() : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    private var _viewBinding: VB? = null

    //This property is only valid between onCreateView and onDestroyView.
    protected val viewBinding: VB get() = _viewBinding!!

    protected abstract val viewModelClass: Class<VM>

    protected abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        _viewBinding = createViewBinding()
        _viewBinding?.root?.also { setContentView(it) }
        Timber.tag(this::class.java.simpleName).d("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    protected abstract fun createViewBinding(): VB?

}
