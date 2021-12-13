package com.trainlingline.part_9_subcomponents

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Scope
import javax.inject.Singleton

fun main() {
    TrainingLineApp().also {
        it.start()
        it.showHomeScreen()
        it.showTickets()
    }
}

class TrainingLineApp {

    lateinit var appComponent: AppComponent


    fun start() {
        appComponent = DaggerAppComponent
            .builder()
            .build()
    }


    fun showHomeScreen() {
        appComponent
            .provideHomeScreenBuilder()
            .build()
            .homeScreenPresenter()
            .present()
    }

    fun showTickets() {
        appComponent
            .provideTicketScreenBuilder()
            .build()
            .ticketScreenPresenter()
            .present()
    }
}


@Singleton
@Component(modules = [UserRepoModule::class, NetworkModule::class])
interface AppComponent {

    fun provideHomeScreenBuilder(): HomeScreenSubcomponent.Builder

    fun provideTicketScreenBuilder(): TicketScreenSubcomponent.Builder

    fun inject(app: TrainingLineApp)

}


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewScope











