package com.mercuriy94.gameoflife.presentation.feature.main

/**
 * @author Nikita Marsyukov
 */
sealed class MainEffect

object StartedLifeEffect : MainEffect()
object PausedLifeEffect : MainEffect()

data class FillFieldEffect(val lifeField: Array<BooleanArray>) : MainEffect() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FillFieldEffect

        if (!lifeField.contentDeepEquals(other.lifeField)) return false

        return true
    }

    override fun hashCode(): Int {
        return lifeField.contentDeepHashCode()
    }
}