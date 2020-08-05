package com.mercuriy94.gameoflife.core.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

/**
 * Абстрактный базовый класс [androidx.lifecycle.ViewModel],
 * где автоматически вызывается метод инъекции зависимостей,
 * который должны наслденики данного класса
 *
 * @author Nikita Marsyukov
 */
abstract class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    /**
     * Метод для отписки и удаления от всех подписок, чтобы не было утечки памяти (Memory leaks)
     * */
    protected fun Disposable.autoDispose() = addTo(disposables)

}
