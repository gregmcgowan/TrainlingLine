package com.trainlingline.part_10_multibindings

import dagger.*
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

    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        appComponent = DaggerAppComponent.create()
        println("------ calling app component helpers")
        appComponent.mapOfHelpers().forEach { it.value.help() }
        println("------ finished calling app component helpers \n")
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
@Component(
    modules = [
        UserRepoModule::class,
        NetworkModule::class,
        HelperElementsIntoSetModule::class,
        MapMultiBindingsComplexKeys::class
    ]
)
interface AppComponent {

    fun setOfHelpers(): Set<Helper>

    fun mapOfHelpers(): Map<HelperType, Helper>

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










