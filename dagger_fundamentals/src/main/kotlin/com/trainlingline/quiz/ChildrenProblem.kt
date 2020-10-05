package com.trainlingline.quiz

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

object ChildrenProblem {

    @Component(modules = [ChildModule::class])
    interface ParentComponent {
        fun childComponent(): ChildComponent.Builder
        fun inject(mainScreen: MainScreen)

    }

    class MainScreen constructor() {
        @Inject lateinit var presenter: Presenter
    }

    class Presenter @Inject constructor(val childDep: ChildDep) {}

    @Subcomponent(modules = [])
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

    class ChildDep @Inject constructor() {
        init { println("I'm $this") }
    }

}

fun main() {
    val mainScreen = ChildrenProblem.MainScreen()
    val parentComponent = DaggerChildrenProblem_ParentComponent.builder().build()
    parentComponent.inject(mainScreen)
    parentComponent.childComponent().build().childDep()


    // What does is displayed when
}
