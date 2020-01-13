package com.trainlingline.part_8_subcomponents

import dagger.*
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton


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


@Singleton
@Component(modules = [UserRepoModule::class, NetworkModule::class])
interface AppComponent {

    fun provideHomeScreenComponentBuilder(): HomeScreenSubComponent.Builder

    fun providerTicketScreenBuilder(): TicketScreenSubComponent.Builder

    fun okHttp(): OkHttpClient

    fun trainLineApp(): TrainingLineApp

    fun userRepo(): UserRepo

    fun inject(app: TrainingLineApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TrainingLineApp): Builder

        fun build(): AppComponent
    }

}


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope


class TrainingLineApp : Navigator {

    lateinit var appComponent: AppComponent

    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()

        homeScreenPresenter = appComponent
            .provideHomeScreenComponentBuilder()
            .build()
            .provideHomeScreenPresenter()

        ticketScreenPresenter = appComponent
            .providerTicketScreenBuilder()
            .build()
            .provideTicketScreenPresenter()
    }


    override fun showHomeScreen() {
        homeScreenPresenter.present()
    }

    override fun showTickets() {
        ticketScreenPresenter.present()
    }
}


fun main() {
    TrainingLineApp().start()
}









