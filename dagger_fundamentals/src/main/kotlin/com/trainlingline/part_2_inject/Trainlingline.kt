package com.trainlingline.part_2_inject

import dagger.Component
import javax.inject.Inject

fun main() {
    TrainingLineApp().start()
}

class TrainingLineApp {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenPresenter

    fun start() {
        DaggerAppComponent
            .create()
            .inject(this)

        homeScreenPresenter.present()
    }
}

@Component
interface AppComponent {

    fun inject(app: TrainingLineApp)

}


class HomeScreenPresenter @Inject constructor(
    private val homeScreen: HomeScreen
) {

    fun present() {
        homeScreen.show()
    }
}

class HomeScreen @Inject constructor() {

    fun show() {
        print("Showing main screen!")
    }
}






