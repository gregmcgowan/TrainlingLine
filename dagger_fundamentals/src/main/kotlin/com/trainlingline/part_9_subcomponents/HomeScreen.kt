package com.trainlingline.part_9_subcomponents

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

@ScreenScope
@Subcomponent(modules = [HomeScreenModule::class])
interface HomeScreenSubcomponent {

    fun homeScreenPresenter(): HomeScreenContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): HomeScreenSubcomponent
    }
}

@Module
interface HomeScreenModule {

    @Binds
    fun bindPresenter(impl: HomeScreenPresenter): HomeScreenContract.Presenter

    @Binds
    fun bindScreen(impl: HomeScreen): HomeScreenContract.Screen


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








