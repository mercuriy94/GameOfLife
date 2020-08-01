package com.mercuriy94.gameoflife.presentation.feature.main

/**
 * @author Nikita Marsyukov
 */
sealed class MainUiEvent

object FillFieldUiEvent : MainUiEvent()
object StartUiEvent : MainUiEvent()
object PauseUiEvent : MainUiEvent()
object StepUiEvent : MainUiEvent()
