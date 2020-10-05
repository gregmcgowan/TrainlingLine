package com.trainlingline.part_3_binds_and_modules

import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject


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
enum class HomeScreenModule { ;

    @Binds
    abstract fun bindPresenter(impl: HomeScreenPresenter): HomeScreenContract.Presenter

    @Binds
    abstract fun bindScreen(impl: HomeScreen): HomeScreenContract.Screen

}


@Component(modules = [HomeScreenModule::class])
interface AppComponent {
    fun inject(app: TrainingLineApp)
}

class TrainingLineApp {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    fun start() {
        DaggerAppComponent.create().inject(this)
        homeScreenPresenter.present()
    }
}


fun main() {
    TrainingLineApp().start()
}




