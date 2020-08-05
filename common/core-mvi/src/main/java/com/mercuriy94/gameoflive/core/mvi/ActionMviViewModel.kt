package com.mercuriy94.gameoflive.core.mvi

/**
 * @author Nikita Marsyukov
 */
abstract class ActionMviViewModel<
        State : Any,
        Action : Any,
        Effect : Any,
        LiveEventData : Any>(
    initialState: State,
    reducer: Reducer<State, Effect>
) : BaseMviViewModel<State, Action, Effect, Action, LiveEventData>(
    initialState = initialState,
    reducer = reducer
) {
    override fun uiEventToAction(uiEvent: Action): Action = uiEvent
}