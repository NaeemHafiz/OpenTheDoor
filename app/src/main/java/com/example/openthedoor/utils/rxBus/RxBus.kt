package com.example.openthedoor.utils.rxBus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus private constructor() {
    private val bus: PublishSubject<Any> = PublishSubject.create()

    fun send(event: Any) {
        bus.onNext(event)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }

    companion object {

        private var rxBus: RxBus? = null

        fun defaultInstance(): RxBus {

            if (rxBus == null) {
                rxBus = RxBus()
            }
            return rxBus as RxBus

        }
    }
}
