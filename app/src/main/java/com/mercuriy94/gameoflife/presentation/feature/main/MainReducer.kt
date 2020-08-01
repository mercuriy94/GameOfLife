package com.mercuriy94.gameoflife.presentation.feature.main

import com.mercuriy94.gameoflife.presentation.base.Reducer

/**
 * @author Nikita Marsyukov
 */
class MainReducer : Reducer<MainViewState, MainEffect> {

    override fun invoke(state: MainViewState, effect: MainEffect): MainViewState =
        when (effect) {
            is FillFieldEffect -> state.copy(lifeField = effect.lifeField)
            StartedLifeEffect -> state.copy(
                isStartVisible = false,
                isPauseVisible = true
            )
            PausedLifeEffect -> state.copy(
                isStartVisible = true,
                isPauseVisible = false
            )
        }

}
