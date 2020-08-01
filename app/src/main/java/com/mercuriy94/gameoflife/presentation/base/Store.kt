package com.mercuriy94.gameoflife.presentation.base

import androidx.lifecycle.LiveData
import ru.inmoso.core.ui.events.LiveEvent

/**
 * @author Nikita Marsyukov
 */
interface Store<in Action : Any, State : Any, Effect : Any, UiEvent : Any, LiveEventData : Any> {

    val viewState: LiveData<State>
    val liveEvent: LiveData<LiveEvent<LiveEventData>>

    fun uiEvent(uiEvent: UiEvent)

}
