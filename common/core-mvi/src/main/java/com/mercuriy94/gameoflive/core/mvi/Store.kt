package com.mercuriy94.gameoflive.core.mvi

import androidx.lifecycle.LiveData
import com.mercuriy94.gameoflife.core.ui.events.LiveEvent

/**
 * @author Nikita Marsyukov
 */
interface Store<in Action : Any, State : Any, Effect : Any, UiEvent : Any, LiveEventData : Any> {

    val viewState: LiveData<State>
    val liveEvent: LiveData<LiveEvent<LiveEventData>>

    fun uiEvent(uiEvent: UiEvent)

}
