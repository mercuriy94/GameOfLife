package com.mercuriy94.gameoflife.core.navigation

import androidx.lifecycle.LiveData
import com.mercuriy94.gameoflife.core.ui.events.LiveEvent

/**
 * Интерфейс предоставляет события навигации для дальнейшей их реализации
 * @author Nikita Marsyukov
 * */
interface NavigationEvents {

    val events: LiveData<LiveEvent<Command>>

}