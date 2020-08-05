package com.mercuriy94.gameoflife.feature.life.presentation

/**
 * @author Nikita Marsyukov
 */
data class LifeViewState(
    val lifeField: Array<BooleanArray>,
    val isStartVisible: Boolean,
    val isPauseVisible: Boolean
) {

    companion object {

        fun initial(): LifeViewState = LifeViewState(
            lifeField = emptyArray(),
            isStartVisible = true,
            isPauseVisible = false
        )

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LifeViewState

        if (!lifeField.contentDeepEquals(other.lifeField)) return false
        if (isStartVisible != other.isStartVisible) return false
        if (isPauseVisible != other.isPauseVisible) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lifeField.contentDeepHashCode()
        result = 31 * result + isStartVisible.hashCode()
        result = 31 * result + isPauseVisible.hashCode()
        return result
    }

}