package com.trainlingline.quiz

import dagger.*
import javax.inject.Inject

object SubComp {

    // parent component
    @Component()
    interface ParentComponent {
        fun childComponentBuilder(): ChildComponent.Builder
    }

    class Context() {}

    // child sub component
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

    class ChildDep constructor() {
        ///
        // can I use UsefulComponent here????

    }

    //another component
    @Component(modules = [UsefulModule::class])
    interface UsefulComponent {
        fun useful(): UsefulClass

        @Component.Builder
        interface Builder {
            fun bindContext(@BindsInstance context: Context) : Builder
            fun build(): UsefulComponent
        }
    }

    @Module
    class UsefulModule {
        @Provides
        fun useful(helper: Helper, context: Context) = UsefulClass(helper, context)
    }

    class UsefulClass(helper: Helper, context: Context) {}

    class Helper @Inject constructor() {}


}
