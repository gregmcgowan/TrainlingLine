package com.trainlingline.part_5_simple_use_of_scopes

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
    private val screen: HomeScreenContract.Screen
) : HomeScreenContract.Presenter {

    override fun present() {
        userRepo.getUser("1")
        // Do some stuff
        screen.show()
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

}


