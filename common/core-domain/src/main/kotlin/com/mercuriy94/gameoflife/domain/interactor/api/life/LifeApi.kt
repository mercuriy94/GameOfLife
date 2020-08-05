package com.mercuriy94.gameoflife.domain.interactor.api.life

import com.mercuriy94.gameoflife.domain.interactor.GameOfLifeInteractor

/**
 * @author Nikita Marsyukov
 */
interface LifeApi {

    fun getGameOfLifeInteractor(): GameOfLifeInteractor

}