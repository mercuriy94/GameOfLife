package com.mercuriy94.gameoflife.feature.life.presentation

import com.mercuriy94.gameoflive.core.mvi.Reducer

/**
 * @author Nikita Marsyukov
 */
class LifeReducer : Reducer<LifeViewState, LifeEffect> {

    override fun invoke(state: LifeViewState, effect: LifeEffect): LifeViewState =
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