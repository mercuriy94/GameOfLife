package com.mercuriy94.gameoflife.domain.usecase

import com.mercuriy94.gameoflife.presentation.feature.main.LIFE_SIZE
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import kotlin.random.Random


/**
 * Класс содержит логику управления клеточным автоматом игры жизнь.
 * Данный класс является потокбезопасным за счет использования синхронизатора [lock]
 * Для каждого следующего шага состояния клеточного автомата выполняются следующие правила:
 * - разыгрывается на клеточном поле;
 * - у каждой клетки 8 соседних клеток;
 * - в каждой клетке может жить существо;
 * - существо с двумя или тремя соседями выживает в следующем поколении, иначе погибает от одиночества или перенаселённости;
 * - в пустой клетке с тремя соседями в следующем поколении рождается существо.
 *
 * @author Nikita Marsyukov
 */
class GameOfLifeInteractor @Inject constructor() {

    /**
     * Синхронизатор.
     * Предполгается что клиентский код будет запрашивать управления
     * клеточным автоматом в многопоточной форме управления.
     * Для этого необходимо синхронизировать управление клеточным автоматом для нескольких потоков.
     * */
    private val lock = ReentrantLock()

    /**
     * Поле жизни, тут кэшируется последнее актуальное значением стейта клеточного автомата
     * Использование [AtomicReference] вообще является избыточным,
     * так как управление поля происходить в синхронихированных блоках [lock]
     * но я нехотел делать ссылку изменяемой
     * */
    private val lifeField = AtomicReference<Array<BooleanArray>>()

    /**
     * Флаг сообщающий о состоянии вычисления следующего состояние клеточного автомата
     * [true] - игра в данный момент идет, в противном случае игра приостановлена
     * */
    private val isRun = AtomicBoolean(false)

    /**
     * Метод для расчета количества живых соседей клетки
     *
     * @param x координата клетки на оси абсцисс для которой нужно выполнить вычисления
     * @param x координата клетки на оси ординат для которой нужно выполнить вычисления
     * @param field поле по которому нужно выполнить вычисленияя
     * */
    private fun countNeighbors(
        x: Int,
        y: Int,
        field: Array<BooleanArray>
    ): Int {

        var count = 0

        for (dx in -1..1) {
            for (dy in -1..1) {
                var nX = x + dx
                var nY = y + dy
                nX = if (nX < 0) LIFE_SIZE - 1 else nX
                nY = if (nY < 0) LIFE_SIZE - 1 else nY
                nX = if (nX > LIFE_SIZE - 1) 0 else nX
                nY = if (nY > LIFE_SIZE - 1) 0 else nY
                count += if (field[nX][nY]) 1 else 0
            }
        }

        if (field[x][y]) count--;

        return count
    }

    /**
     * Расчитать следующего состояние клеточного автомата
     *
     * @param lifeField исходнае состояние клеточного автомата для которого нужно выполнить расчет
     * @return рассчитаное следующее состояние клеточного автомата
     * */
    private fun calculateNextLifeState(lifeField: Array<BooleanArray>?): Array<BooleanArray> =
        lifeField?.let { field ->
            field.mapIndexed { x, booleans ->

                booleans.mapIndexed yArray@{ y, isAlive ->
                    val countNeighbors = countNeighbors(x, y, field)
                    return@yArray when {
                        countNeighbors == 3 -> true
                        countNeighbors < 2 || countNeighbors > 3 -> false
                        else -> isAlive
                    }

                }.toBooleanArray()
            }.toTypedArray()

        } ?: Array(LIFE_SIZE) { BooleanArray(LIFE_SIZE) { Random.nextBoolean() } }

    /**
     * Начать игру
     *
     * @return наблюдаемый который с временным интервалом испускает итерацию шага  жизни клеточного автомата
     * */
    fun start(): Observable<Array<BooleanArray>> {
        return Completable.fromAction { isRun.getAndSet(true) }
            .andThen(
                Observable.interval(300, TimeUnit.MILLISECONDS, Schedulers.io())
                    .map {

                        lock.lock()

                        try {

                            val newField = calculateNextLifeState(lifeField.get())

                            if (isRun.get()) {
                                lifeField.getAndSet(newField)
                            }

                            newField

                        } finally {
                            lock.unlock()
                        }

                    }

            )
            .takeWhile { isRun.get() }
    }

    /**
     * Заполнить поле случайными жильцами
     * @return Поле заполненное случайными жильцами
     * */
    fun fill(): Single<Array<BooleanArray>> = Single.fromCallable {
        try {
            lock.lock()
            val newField = Array(LIFE_SIZE) { BooleanArray(LIFE_SIZE) { Random.nextBoolean() } }
            lifeField.getAndSet(newField)
            newField
        } finally {
            lock.unlock()
        }
    }

    /**
     * Поставить игру на паузу
     * */
    fun pause(): Completable = Completable.fromAction {
        isRun.getAndSet(false)
    }

    /**
     * Вычисляет следующую итерацию состояния клеточного автомата
     * */
    fun step(): Single<Array<BooleanArray>> = pause()
        .andThen(
            Single.fromCallable {
                try {
                    lock.lock()
                    val newField = calculateNextLifeState(lifeField.get())
                    lifeField.getAndSet(newField)
                    newField
                } finally {
                    lock.unlock()
                }
            }
        )

}
