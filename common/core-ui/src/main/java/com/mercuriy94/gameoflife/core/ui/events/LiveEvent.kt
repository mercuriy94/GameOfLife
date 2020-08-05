package com.mercuriy94.gameoflife.core.ui.events

import androidx.lifecycle.Observer

open class LiveEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

/**
 * An [Observer] for [LiveEvent]s, simplifying the pattern of checking if the [LiveEvent]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [LiveEvent]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.handle(onEventUnhandledContent)
    }
}

inline fun <T> LiveEvent<T>.handle(onEventUnhandledContent: (T) -> Unit) {
    getContentIfNotHandled()?.let { value ->
        onEventUnhandledContent(value)
    }
}
