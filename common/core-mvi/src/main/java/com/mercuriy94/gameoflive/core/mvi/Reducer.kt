package com.mercuriy94.gameoflive.core.mvi

/**
 * @author Nikita Marsyukov
 */
typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State
