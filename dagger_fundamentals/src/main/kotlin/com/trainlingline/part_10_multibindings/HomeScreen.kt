package com.trainlingline.part_10_multibindings

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoSet
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

    @Binds
    @IntoSet
    fun bindHomeScreenHelper(impl: HomeScreenHelper): Helper

}

class HomeScreenHelper @Inject constructor() : Helper {
    override fun help() {
        println("I'm helping on the home screen!!!")
    }
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
    private val helpers: Set<@JvmSuppressWildcards Helper>,
    private val userRepo: UserRepo,
    private val screen: HomeScreenContract.Screen
) : HomeScreenContract.Presenter {

    override fun present() {
        println("----- calling helpers on the home screen")
        helpers.forEach { it.help() }
        println("----- finished helpers on the home screen \n")

        userRepo.getUser("1")
        // Do some stuff
        screen.show()

    }
}

class HomeScreen @Inject constructor() : HomeScreenContract.Screen {

    override fun show() {

    }
}








