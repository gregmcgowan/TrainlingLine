package com.trainlingline.part_9_subcomponents

import dagger.*
import okhttp3.OkHttpClient
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(modules = [UserRepoModule::class, NetworkModule::class])
interface AppComponent {

    fun provideHomeScreenBuilder(): HomeScreenSubcomponent.Builder

    fun providerTicketScreenBuilder(): TicketScreenSubcomponent.Builder

    fun trainLineApp(): TrainingLineApp

    fun inject(app: TrainingLineApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TrainingLineApp): Builder

        fun build(): AppComponent
    }

}

@Module(subcomponents = [TicketScreenSubcomponent::class])
interface SubComponentModule {

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewScope


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}

interface Navigator {

    fun showHomeScreen()

    fun showTickets()

}

class TrainingLineApp : Navigator {

    lateinit var appComponent: AppComponent

    lateinit var homeScreenPresenter: HomeScreenContract.Presenter
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()


        ticketScreenPresenter = appComponent
            .providerTicketScreenBuilder()
            .build()
            .ticketScreenPresenter()
    }


    override fun showHomeScreen() {
        appComponent
            .provideHomeScreenBuilder()
            .build()
            .homeScreenPresenter()
            .present()
    }

    override fun showTickets() {
        ticketScreenPresenter.present()
    }
}


fun main() {
    val trainingLineApp = TrainingLineApp()
    trainingLineApp.start()
    trainingLineApp.showHomeScreen()
    trainingLineApp.showTickets()
}








