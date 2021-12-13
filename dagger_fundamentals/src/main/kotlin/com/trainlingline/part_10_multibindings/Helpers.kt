package com.trainlingline.part_10_multibindings

import javax.inject.Inject

interface Helper {
    fun help()
}

class GoodHelper @Inject constructor() : Helper {
    override fun help() {
        println("I'm good at helping!")
    }
}

class EvenBetterHelper @Inject constructor() : Helper {
    override fun help() {
        println("I'm even better at helping! ")
    }
}
