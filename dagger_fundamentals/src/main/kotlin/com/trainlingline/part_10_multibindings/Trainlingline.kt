package com.trainlingline.part_10_multibindings

import dagger.*
import okhttp3.OkHttpClient
import javax.inject.Scope
import javax.inject.Singleton

fun main() {
    TrainingLineApp().start()
}


@Singleton
@Component(
    modules = [
        UserRepoModule::class,
        NetworkModule::class,
        HelperElementsIntoSetModule::class,
        MapMultiBindingsComplexKeys::class]
)
interface AppComponent {

    fun setOfHelpers(): Set<Helper>

    fun mapOfHelpers(): Map<HelperType, Helper>

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

class TrainingLineApp : Navigator {

    lateinit var appComponent: AppComponent
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
       // appComponent.setOfHelpers().forEach { it.help() }
        //appComponent.setOfHelpers().forEach { it.help() }
        appComponent.mapOfHelpers().forEach { it.value.help() }

        //println("new line")
        homeScreenPresenter = appComponent
            .provideHomeScreenBuilder()
            .build()
            .homeScreenPresenter()

        ticketScreenPresenter = appComponent
            .providerTicketScreenBuilder()
            .build()
            .ticketScreenPresenter()
        showHomeScreen()

//        println("new line")
//        appComponent.setOfHelpers().forEach { it.help() }

    }


    override fun showHomeScreen() {
        homeScreenPresenter.present()
    }

    override fun showTickets() {
        ticketScreenPresenter.present()
    }
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










