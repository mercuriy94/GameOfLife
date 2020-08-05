@file:Suppress("MethodOverloading")

package com.mercuriy94.gameoflife.core.ui.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object Constants {

    internal const val DELAY_TIME = 300L
}

fun <T> Observable<T>.applySchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> {
    return subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
}

fun <T> Single<T>.applySchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Single<T> {
    return subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
}

fun Completable.applySchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Completable {
    return subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
}

fun <T> Maybe<T>.applySchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Maybe<T> {
    return subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
}

fun <T> Flowable<T>.applySchedulers(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Flowable<T> {
    return subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
}

fun Completable.withDelayedAction(
    delayTime: Long = Constants.DELAY_TIME,
    units: TimeUnit = TimeUnit.MILLISECONDS,
    action: () -> Unit
): Completable {
    var delayed: Disposable? = null
    return this.doOnSubscribe {
        delayed = delayedCompletable(delayTime, units, action).subscribe()
    }.doFinally { delayed?.dispose() }
}

fun <T> Single<T>.withDelayedAction(
    delayTime: Long = Constants.DELAY_TIME,
    units: TimeUnit = TimeUnit.MILLISECONDS,
    action: () -> Unit
): Single<T> {
    var delayed: Disposable? = null
    return this
        .doOnSubscribe {
            delayed = delayedCompletable(delayTime, units, action).subscribe()
        }
        .doFinally { delayed?.dispose() }
}

fun <T> Observable<T>.withDelayedAction(
    delayTime: Long = Constants.DELAY_TIME,
    units: TimeUnit = TimeUnit.MILLISECONDS,
    action: () -> Unit
): Observable<T> {
    var delayed: Disposable? = null
    return this
        .doOnSubscribe {
            delayed = delayedCompletable(delayTime, units, action).subscribe()
        }
        .doFinally { delayed?.dispose() }
}

fun <T> Flowable<T>.withDelayedAction(
    delayTime: Long = Constants.DELAY_TIME,
    units: TimeUnit = TimeUnit.MILLISECONDS,
    action: () -> Unit
): Flowable<T> {
    var delayed: Disposable? = null
    return this
        .doOnSubscribe {
            delayed = delayedCompletable(delayTime, units, action).subscribe()
        }
        .doFinally { delayed?.dispose() }
}

private fun delayedCompletable(
    delayTime: Long,
    units: TimeUnit,
    action: () -> Unit
): Completable = Completable.timer(delayTime, units)
    .observeOn(AndroidSchedulers.mainThread()).
    doOnComplete(action)

