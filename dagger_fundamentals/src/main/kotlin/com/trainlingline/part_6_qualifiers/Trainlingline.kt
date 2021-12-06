package com.trainlingline.part_6_qualifiers

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

fun main() {
    TrainingLineApp().start()
}

class TrainingLineApp {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    @Inject
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        DaggerAppComponent.builder()
            .ticketScreenModule(TicketScreenModule("Greg", "1"))
            .build()
            .inject(this)

        showHomeScreen()
        // after selecting user
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












