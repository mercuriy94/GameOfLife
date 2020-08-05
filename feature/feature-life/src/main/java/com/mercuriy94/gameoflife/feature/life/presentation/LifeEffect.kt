package com.mercuriy94.gameoflife.feature.life.presentation

/**
 * @author Nikita Marsyukov
 */
sealed class LifeEffect

object StartedLifeEffect : LifeEffect()
object PausedLifeEffect : LifeEffect()

data class FillFieldEffect(val lifeField: Array<BooleanArray>) : LifeEffect() {
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