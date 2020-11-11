package com.trainlingline.hustle

import kotlin.reflect.KClass

open class HustleApp {

    private var current: Hustle? = null

    fun <H : Hustle> start(hustle: KClass<H>) {
        current?.app = null
        current?.stop()
        current = hustle.constructors.first().call()
        current?.app = this
        current?.start()
    }

}


open class Hustle {

    var app: HustleApp? = null

    open fun start() {}

    open fun stop() {}

}





