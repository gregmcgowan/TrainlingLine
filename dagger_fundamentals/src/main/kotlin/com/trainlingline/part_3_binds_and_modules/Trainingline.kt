package com.trainlingline.part_3_binds_and_modules

import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject

fun main() {
    TrainingLineApp().start()
}

class TrainingLineApp {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    fun start() {
        DaggerAppComponent.create().inject(this)
        homeScreenPresenter.present()
    }
}

@Component(modules = [HomeScreenModule::class])
interface AppComponent {
    fun inject(app: TrainingLineApp)
}


interface HomeScreenContract {

    interface Presenter {
        fun present()
    }

    interface Screen {
        fun show()
    }
}


class HomeScreenPresenter @Inject constructor(
    private val mainScreen: HomeScreenContract.Screen
) : HomeScreenContract.Presenter {

    override fun present() {
        mainScreen.show()
    }
}

class HomeScreen @Inject constructor() : HomeScreenContract.Screen {

    override fun show() {
        print("Showing main screen")
    }
}


@Module
interface HomeScreenModule {;

    @Binds
    fun bindPresenter(impl: HomeScreenPresenter): HomeScreenContract.Presenter

    @Binds
    fun bindScreen(impl: HomeScreen): HomeScreenContract.Screen

}







