package com.mercuriy94.gameoflife.feature.life.presentation

/**
 * @author Nikita Marsyukov
 */
sealed class LifeAction

data class UiEventAction(val uiEvent: LifeUiEvent) : LifeAction()
