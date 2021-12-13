package com.trainlingline.part_7_binds_instance

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named
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
        DaggerAppComponent
            .builder()
            .userId("1")
            .userUsername("greg")
            .build()
            .inject(this)


//        DaggerAppComponent
//            .factory()
//            .appComponent("1","GREG")
//            .inject(this)

        showHomeScreen()
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

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        UserRepoModule::class,
        HomeScreenModule::class,
        TicketRepoModule::class,
        TicketScreenModule::class
    ]
)
interface AppComponent {

    fun inject(app: TrainingLineApp)
//
//    @Component.Factory
//    interface Factory {
//
//        fun appComponent(
//            @BindsInstance @Named("userId") userId: String,
//            @BindsInstance @Named("userName") userName: String
//        ): AppComponent
//    }

    @Component.Builder
    interface Builder {

        fun userId(@BindsInstance @Named("userId") userId: String): Builder
        fun userUsername(@BindsInstance @Named("userName") userName: String): Builder
        fun build(): AppComponent
    }

}

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient()

}













