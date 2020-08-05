package com.mercuriy94.gameoflife.feature.life.presentation

/**
 * @author Nikita Marsyukov
 */
sealed class LifeUiEvent

object FillFieldUiEvent : LifeUiEvent()
object StartUiEvent : LifeUiEvent()
object PauseUiEvent : LifeUiEvent()
object StepUiEvent : LifeUiEvent()
