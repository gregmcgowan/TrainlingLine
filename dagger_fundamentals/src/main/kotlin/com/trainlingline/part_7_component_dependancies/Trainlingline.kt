package com.trainlingline.part_7_component_dependancies

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Scope
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope


@Singleton
@Component(modules = [UserRepoModule::class, NetworkModule::class])
interface AppComponent {

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


interface Navigator {

    fun showHomeScreen()

    fun showTickets()

}


class TrainingLineApp : Navigator {

    lateinit var appComponent: AppComponent

    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()


        homeScreenPresenter = DaggerHomeScreenComponent
            .builder()
            .appComponent(appComponent)
            .build()
            .provideHomeScreenPresenter()

        ticketScreenPresenter = DaggerTicketScreenComponent
            .builder()
            .appComponent(appComponent)
            .build()
            .provideTicketScreenPresenter()

        showHomeScreen()
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









