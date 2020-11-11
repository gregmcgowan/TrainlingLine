package com.trainlingline.hustle

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UserRepoModule::class,
        NetworkModule::class
    ]
)
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

class TrainingLineApp : Navigator, HustleApp() {

    lateinit var appComponent: AppComponent

    fun start() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        appComponent.inject(this)
    }


    override fun showHomeScreen() {
        start(HomeHustle::class)
    }

    override fun showTickets() {
        start(TicketHustle::class)
    }
}


fun main() {
    val trainingLineApp = TrainingLineApp()
    trainingLineApp.start()
    trainingLineApp.showHomeScreen()
    trainingLineApp.showTickets()
}









