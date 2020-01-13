package com.trainlingline.part_2_inject

import dagger.Component
import javax.inject.Inject

class HomeScreen @Inject constructor() {

    fun show() {
        print("Showing main screen!")
    }
}

class HomeScreenPresenter @Inject constructor(
    private val homeScreen: HomeScreen
) {

    fun present() {
        homeScreen.show()
    }
}


@Component
interface AppComponent {

    fun inject(app: TrainingLineApp)

}


class TrainingLineApp {

    @Inject lateinit var homeScreenPresenter: HomeScreenPresenter

    fun start() {
        DaggerAppComponent.create().inject(this)
        homeScreenPresenter.present()
    }
}

fun main() {
    TrainingLineApp().start()
}



