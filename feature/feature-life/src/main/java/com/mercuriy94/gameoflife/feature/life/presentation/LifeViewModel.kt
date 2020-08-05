package com.mercuriy94.gameoflife.feature.life.presentation

import com.mercuriy94.gameoflife.core.ui.extensions.applySchedulers
import com.mercuriy94.gameoflife.domain.interactor.GameOfLifeInteractor
import com.mercuriy94.gameoflive.core.mvi.BaseMviViewModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Nikita Marsyukov
 */
class LifeViewModel @Inject constructor(private val gameOfLifeInteractor: GameOfLifeInteractor) :
    BaseMviViewModel<
            LifeViewState,
            LifeAction,
            LifeEffect,
            LifeUiEvent,
            String>(
        reducer = LifeReducer(),
        initialState = LifeViewState.initial()
    ) {

    override fun handleAction(
        state: LifeViewState,
        action: LifeAction
    ): Observable<out LifeEffect> =
        when (action) {
            is UiEventAction -> when (action.uiEvent) {
                FillFieldUiEvent -> gameOfLifeInteractor.fill()
                    .map { FillFieldEffect(it) }
                    .toObservable()
                    .applySchedulers()
                StartUiEvent -> gameOfLifeInteractor.start()
                    .map { FillFieldEffect(it) as LifeEffect }
                    .startWith(StartedLifeEffect)
                    .applySchedulers()
                PauseUiEvent -> gameOfLifeInteractor.pause()
                    .andThen(Observable.just(PausedLifeEffect))
                    .applySchedulers()
                StepUiEvent -> gameOfLifeInteractor.step()
                    .map { FillFieldEffect(it) as LifeEffect }
                    .toObservable()
                    .startWith(PausedLifeEffect)
                    .applySchedulers()
            }
        }

    override fun uiEventToAction(uiEvent: LifeUiEvent): LifeAction = UiEventAction(uiEvent)

}
