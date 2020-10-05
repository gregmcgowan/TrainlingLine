package com.trainlingline.part_8_component_dependancies

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun okHttp(): OkHttpClient

    fun trainLineApp(): TrainingLineApp

    fun inject(app: TrainingLineApp)

    @Component.Builder
    interface Builder {
        fun app(@BindsInstance app: TrainingLineApp): Builder
        fun build(): AppComponent
    }

}


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewScope

interface OtherComponent {
    fun doSomething(): String
}

@ViewScope
@Component(modules = [OtherModule::class])
interface OtherComponentImpl : OtherComponent {

}

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}


@Module
class OtherModule {

    @Provides
    fun string(): String = "greg"
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
        appComponent = DaggerAppComponent.builder().app(this).build()

        val homeScreenComponent = DaggerHomeScreenComponent
            .builder()
            .appComponent(appComponent)
            .build()
        homeScreenPresenter = homeScreenComponent.provideHomeScreenPresenter()

        val ticketScreenComponent = DaggerTicketScreenComponent
            .builder()
            .appComponent(appComponent)
            .build()
        ticketScreenPresenter = ticketScreenComponent.provideTicketScreenPresenter()

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









