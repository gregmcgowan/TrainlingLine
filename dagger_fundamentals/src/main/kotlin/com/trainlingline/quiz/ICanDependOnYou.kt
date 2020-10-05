package com.trainlingline.quiz

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

//object ICanDependOnYou {
//
//    @Component(dependencies = [SiblingComponent::class])
//    interface MainComponent {
//        fun inject(mainScreen: MainScreen)
//    }
//
//    @Module
//    class ParentModule {
//        @Provides
//        fun theString() = "GOD"
//    }
//
//    class MainScreen constructor() {
//
//        @Inject
//        lateinit var presenter: Presenter
//
//    }
//
//    class Presenter @Inject constructor(sibDep: SibDep) {
//
//    }
//
//    @Singleton
//    @Component(modules = [SiblingModule::class])
//    interface SiblingComponent {
//        fun sibDep(): SibDep
//    }
//
//    @Module
//    class SiblingModule {
//
//        @Singleton
//        @Provides
//        fun sibDep() = SibDep()
//
//    }
//
//    class SibDep()
//
//    @Scope
//    @Retention(AnnotationRetention.RUNTIME)
//    annotation class CustomScope
//
//    @Scope
//    @Retention(AnnotationRetention.RUNTIME)
//    annotation class OtherCustomScope
//}
//
//
//fun main() {
//
//}
