package com.trainlingline.part_6_binds_instance

import dagger.Binds
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
    private val userRepo: UserRepo,
    private val screen: HomeScreenContract.Screen,
    private val navigator: Navigator
) : HomeScreenContract.Presenter {

    override fun present() {
        userRepo.getUser("1")
        // Do some stuff
        screen.show()

        // after some user input
        navigator.showTickets()
    }
}

class HomeScreen @Inject constructor() : HomeScreenContract.Screen {

    override fun show() {
        println("Showing main screen")
    }
}

@Module
interface HomeScreenModule {

    @Binds
    fun bindPresenter(impl: HomeScreenPresenter): HomeScreenContract.Presenter

    @Binds
    fun bindScreen(impl: HomeScreen): HomeScreenContract.Screen

    @Binds
    fun bindNavigator(impl : TrainingLineApp) : Navigator

}


