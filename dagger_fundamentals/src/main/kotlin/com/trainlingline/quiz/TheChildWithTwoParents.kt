package com.trainlingline.quiz

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

object TheChildWithTwoParents {

    @Component
    interface Parent1Component {
        fun child1Builder(): Child1Component.Builder
    }

    @Component()
    interface Parent2Component {
        fun child1Builder(): Child1Component.Builder
    }

    @Subcomponent(modules = [Child1Module::class])
    interface Child1Component {
        fun childDep(): ChildDep

        @Subcomponent.Builder
        interface Builder {
            fun build(): Child1Component
        }

    }

    @Module
    class Child1Module {
        @Provides
        fun childDep() = ChildDep()
    }

    class ChildDep() {
        init {
            println("I'm $this")
        }
    }
}

fun main() {
    DaggerTheChildWithTwoParents_Parent1Component.create().child1Builder().build().childDep()
    DaggerTheChildWithTwoParents_Parent2Component.create().child1Builder().build().childDep()
}
