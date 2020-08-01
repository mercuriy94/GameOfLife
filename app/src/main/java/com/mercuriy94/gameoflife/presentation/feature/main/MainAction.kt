package com.mercuriy94.gameoflife.presentation.feature.main

/**
 * @author Nikita Marsyukov
 */
sealed class MainAction

data class UiEventAction(val uiEvent: MainUiEvent) : MainAction()
