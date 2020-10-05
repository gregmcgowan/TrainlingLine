package com.trainlingline.quiz

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Singleton

object ICantScope {

    @Component()
    interface ParentComponent {
        fun childComponentBuilder(): ChildComponent.Builder
        fun inject(mainScreen: MainScreen)

    }

    class MainScreen constructor() {
        @Inject
        lateinit var presenter: Presenter
    }

    class Presenter @Inject constructor() {}

    @Subcomponent(modules = [ChildModule::class])
    interface ChildComponent {
        fun childDep(): ChildDep

        @Subcomponent.Builder
        interface Builder {
            fun build(): ChildComponent
        }
    }

    @Module
    class ChildModule {

        @Provides
        fun childDep() = ChildDep()

    }

    class ChildDep constructor()

}

fun main() {

}