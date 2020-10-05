package com.trainlingline.part_7_binds_instance

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        UserRepoModule::class,
        HomeScreenModule::class,
        TicketRepoModule::class,
        TicketScreenModule::class
    ]
)
interface AppComponent {

    fun inject(app: TrainingLineApp)

    @Component.Factory
    interface Factory {

        fun newComponent(
            @BindsInstance @Named("userId") userId: String,
            @BindsInstance @Named("userName") userName: String
        ): AppComponent
    }

}

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}


class TrainingLineApp {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter
    @Inject
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        DaggerAppComponent
            .factory()
            .newComponent("id", "username")
            .inject(this)

        showHomeScreen()
        showTickets()
    }


    fun showHomeScreen() {
        homeScreenPresenter.present()
    }

    // not used yet but will be
    fun showTickets() {
        ticketScreenPresenter.present()
    }
}


fun main() {
    TrainingLineApp().start()
}







