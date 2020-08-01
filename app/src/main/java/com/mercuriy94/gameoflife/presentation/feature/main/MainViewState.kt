package com.mercuriy94.gameoflife.presentation.feature.main

/**
 * @author Nikita Marsyukov
 */
data class MainViewState(
    val lifeField: Array<BooleanArray>,
    val isStartVisible: Boolean,
    val isPauseVisible: Boolean
) {

    companion object {

        fun initial(): MainViewState = MainViewState(
            lifeField = emptyArray(),
            isStartVisible = true,
            isPauseVisible = false
        )

    }

}
