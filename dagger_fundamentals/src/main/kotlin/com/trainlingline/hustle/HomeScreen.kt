package com.trainlingline.hustle

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

class HomeHustle : Hustle() {

    @Inject lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    override fun start() {
        InjectionOfHustle.inject(this)
        homeScreenPresenter.present()
    }

    override fun stop() {
        homeScreenPresenter.stop()
    }
}

@ScreenScope
@Subcomponent(modules = [HomeScreenModule::class])
interface HomeScreenSubcomponent : HustleInjector<HomeHustle> {

    fun homeScreenPresenter(): HomeScreenContract.Presenter

    @Subcomponent.Builder
    interface Builder : HustleInjector.Factory<HomeHustle> {
        override fun build(): HomeScreenSubcomponent
    }
}

@Module
interface HomeScreenModule {

    @Binds
    fun bindPresenter(impl: HomeScreenPresenter): HomeScreenContract.Presenter

    @Binds
    fun bindScreen(impl: HomeScreen): HomeScreenContract.Screen

    @Binds
    fun bindNavigator(impl: TrainingLineApp): Navigator

}

interface HomeScreenContract {

    interface Presenter {
        fun present()
        fun stop()
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

    override fun stop() {

    }
}

class HomeScreen @Inject constructor() : HomeScreenContract.Screen {

    override fun show() {
        println("Showing main screen")
    }
}








