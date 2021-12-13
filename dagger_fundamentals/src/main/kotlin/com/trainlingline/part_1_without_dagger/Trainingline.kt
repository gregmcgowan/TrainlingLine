package com.trainlingline.part_1_without_dagger


fun main() {
    TrainingLineApp().start()
}


class TrainingLineApp {

    lateinit var presenter: HomeScreenPresenter

    fun start() {
        presenter = HomeScreenPresenter(HomeScreen())
        presenter.present()
    }
}


class HomeScreen {

    fun show() {
        print("Showing main screen!")
    }
}

class HomeScreenPresenter(private val homeScreen: HomeScreen) {

    fun present() {
        homeScreen.show()
    }
}




