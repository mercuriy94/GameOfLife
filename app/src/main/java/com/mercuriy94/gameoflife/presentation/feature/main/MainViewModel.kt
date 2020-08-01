package com.mercuriy94.gameoflife.presentation.feature.main

import com.mercuriy94.gameoflife.domain.usecase.GameOfLifeInteractor
import com.mercuriy94.gameoflife.presentation.base.BaseMviViewModel
import io.reactivex.Observable
import ru.inmoso.core.ui.extensions.applySchedulers

/**
 * @author Nikita Marsyukov
 */
class MainViewModel constructor(private val gameOfLifeInteractor: GameOfLifeInteractor) :
    BaseMviViewModel<
        MainViewState,
        MainAction,
        MainEffect,
        MainUiEvent,
        String>(
    reducer = MainReducer(),
    defaultInitialState = MainViewState.initial()
) {

    override fun handleAction(
        state: MainViewState,
        action: MainAction
    ): Observable<out MainEffect> =
        when (action) {
            is UiEventAction -> when (action.uiEvent) {
                FillFieldUiEvent -> gameOfLifeInteractor.fill()
                    .map { FillFieldEffect(it) }
                    .toObservable()
                    .applySchedulers()
                StartUiEvent -> gameOfLifeInteractor.start()
                    .map { FillFieldEffect(it) as MainEffect }
                    .startWith(StartedLifeEffect)
                    .applySchedulers()
                PauseUiEvent -> gameOfLifeInteractor.pause()
                    .andThen(Observable.just(PausedLifeEffect))
                    .applySchedulers()
                StepUiEvent -> gameOfLifeInteractor.step()
                    .map { FillFieldEffect(it) as MainEffect }
                    .toObservable()
                    .startWith(PausedLifeEffect)
                    .applySchedulers()
            }
        }

    override fun uiEventToAction(uiEvent: MainUiEvent): MainAction = UiEventAction(uiEvent)

}
