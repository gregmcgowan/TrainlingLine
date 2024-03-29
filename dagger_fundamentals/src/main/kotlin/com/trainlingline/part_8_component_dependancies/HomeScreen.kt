package com.trainlingline.part_8_component_dependancies

import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject

@ScreenScope
@Component(
    modules = [HomeScreenModule::class, UserRepoModule::class],
    dependencies = [AppComponent::class, OtherComponent::class]
)
interface HomeScreenComponent {

    fun provideHomeScreenPresenter(): HomeScreenContract.Presenter
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




