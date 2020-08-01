package com.mercuriy94.gameoflife.presentation.base

/**
 * @author Nikita Marsyukov
 */
typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State
