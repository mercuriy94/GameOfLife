package com.mercuriy94.gameoflife.core.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Абстрактный базовый класс фрагмента [androidx.fragment.app.Fragment],
 * где автоматически вызывается метод инъекции зависимостей,
 * который должны наслденики данного класса
 *
 * @author Nikita Marsyukov
 */
abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val compositeDisposable = CompositeDisposable()

    private var _viewBinding: VB? = null

    //This property is only valid between onCreateView and onDestroyView.
    protected val viewBinding: VB get() = _viewBinding!!

    protected abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = createViewBinding(inflater, container, savedInstanceState)
        return _viewBinding?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInsets()
    }

    /**
     * Метод предназначен для установки инстетов на вью элементы
     * */
    protected open fun setupInsets() {
        //do override
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
        _viewBinding = null
    }

    /**
     * Метод где должна производится инъекция необходимых зависимостей
     * */
    protected abstract fun inject()

    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB?

}
