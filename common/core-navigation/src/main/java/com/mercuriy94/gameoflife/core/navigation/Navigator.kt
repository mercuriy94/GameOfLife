package com.mercuriy94.gameoflife.core.navigation

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercuriy94.gameoflife.core.ui.events.LiveEvent
import javax.inject.Inject


/**
 * Навигатор для передачи команд на выполнение через [NavigationEvents]
 *
 * @author Nikita Marsyukov
 * */
interface Navigator : NavigationEvents {

    fun applyCommand(command: Command)

}

/*
* Обычная реализация навигатора
* */
class NavigatorImpl @Inject constructor() : Navigator {

    private val navigationLiveData: MutableLiveData<LiveEvent<Command>> by lazy {
        MutableLiveData<LiveEvent<Command>>()
    }

    override val events: LiveData<LiveEvent<Command>> = navigationLiveData

    override fun applyCommand(command: Command) {
        if (Looper.getMainLooper().thread !== Thread.currentThread()) {
            navigationLiveData.postValue(LiveEvent(command))
        } else {
            navigationLiveData.value = LiveEvent(command)
        }
    }

}

class NavigatorHolderViewModel(val navigator: Navigator) : ViewModel() {

    @Inject
    constructor() : this(NavigatorImpl())

}

/**
 * Необзятально использовать именно этот [Navigator]
 * Можно использовать любую желаему структура для предоставления [Navigator]
 * */
open class NavigationViewModel @Inject constructor(
    private val backRouter: BackRouter,
    navigator: Navigator
) : ViewModel(),
    NavigationEvents by navigator,
    BackRouter by backRouter
