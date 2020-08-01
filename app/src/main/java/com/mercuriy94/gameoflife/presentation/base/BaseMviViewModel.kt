package com.mercuriy94.gameoflife.presentation.base

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import ru.inmoso.core.ui.events.LiveEvent

/**
 * @author Nikita Marsyukov
 */
abstract class BaseMviViewModel<State : Any,
        Action : Any,
        Effect : Any,
        UiEvent : Any,
        LiveEventData : Any>(
    defaultInitialState: State,
    private val reducer: Reducer<State, Effect>
) : ViewModel(), Store<Action, State, Effect, UiEvent, LiveEventData> {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    private val _initialState = defaultInitialState
    private val _statePublisher = BehaviorRelay.createDefault(_initialState)

    private val _liveEventPublisher = BehaviorRelay.create<LiveEventData>()

    private val _viewState = MutableLiveData<State>()
    override val viewState: LiveData<State> = _viewState

    private val _liveEvent = MutableLiveData<LiveEvent<LiveEventData>>()
    override val liveEvent: LiveData<LiveEvent<LiveEventData>> = _liveEvent


    init {
        _statePublisher
            .subscribeBy(onNext = { newState ->
                _viewState.value = newState
            })
            .autoDispose()

        _liveEventPublisher
            .subscribeBy(onNext = { liveEventData ->
                _liveEvent.value = LiveEvent(liveEventData)
            })
            .autoDispose()
    }

    protected fun callAction(action: Action) = processAction(action)

    private fun processAction(action: Action) {
        val state = _statePublisher.value ?: _initialState

        if (isIgnoreAction(state, action)) return

        handleAction(state, action)
            .withLatestFrom(
                _statePublisher,
                BiFunction<Effect, State, Pair<Effect, State>> { t1, t2 -> t1 to t2 })
            .subscribeBy(onNext = { pair -> invokeReducer(pair.second, action, pair.first) })
            .autoDispose()
    }

    protected open fun isIgnoreAction(state: State, action: Action): Boolean = false

    private fun invokeReducer(state: State, action: Action, effect: Effect) {
        checkThread()
        val newState = reducer(state, effect)
        _statePublisher.accept(newState)
        processLiveEventData(state, action, effect)
        postProcess(state, action, effect)
    }

    //do override
    protected open fun onLiveEventData(
        state: State,
        action: Action,
        effect: Effect
    ): LiveEventData? = null

    //do override
    protected open fun postProcess(state: State, action: Action, effect: Effect): Unit = Unit

    private fun processLiveEventData(state: State, action: Action, effect: Effect) {
        onLiveEventData(
            state,
            action,
            effect
        )?.also { liveEventData ->
            _liveEventPublisher.accept(liveEventData)
        }
    }

    private fun checkThread() {
        check(Looper.getMainLooper().thread === Thread.currentThread()) {
            "Require main thread!"
        }
    }

    override fun uiEvent(uiEvent: UiEvent) = processAction(uiEventToAction(uiEvent))

    protected abstract fun handleAction(state: State, action: Action): Observable<out Effect>

    protected abstract fun uiEventToAction(uiEvent: UiEvent): Action

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    /**
     * Метод для отписки и удаления от всех подписок, чтобы не было утечки памяти (Memory leaks)
     * */
    protected fun Disposable.autoDispose() = addTo(disposables)

}
