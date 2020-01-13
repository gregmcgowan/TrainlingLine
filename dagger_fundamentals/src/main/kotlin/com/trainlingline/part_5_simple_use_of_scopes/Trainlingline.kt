package com.trainlingline.part_5_simple_use_of_scopes

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}


@Singleton
@Component(
    modules = [
        NetworkModule::class,
        UserRepoModule::class,
        TicketRepoModule::class,
        HomeScreenModule::class,
        TicketScreenModule::class
    ]
)
interface AppComponent {

    fun inject(app: TrainingLineApp)
}


class TrainingLineApp {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    @Inject
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        DaggerAppComponent.create().inject(this)
        showHomeScreen()
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






