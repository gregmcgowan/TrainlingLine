package com.trainlingline.part_6_binds_instance

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
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

    /**
     * We can override the existing default builder by doing this
     */
    @Component.Builder
    interface Builder {

        /**
         * @BindsInstance allows us to insert a object into the graph. This can be used
         * to add objects that we don't control the creation of.
         *
         * Once it is in the graph we can use @Bind elsewhere to inject it.
         *
         */
        @BindsInstance
        fun application(application: TrainingLineApp): Builder

        fun build(): AppComponent
    }

}


class TrainingLineApp : Navigator {

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter

    @Inject
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    fun start() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        showHomeScreen()
    }


    override fun showHomeScreen() {
        homeScreenPresenter.present()
    }

    // not used yet but will be
    override fun showTickets() {
        ticketScreenPresenter.present()
    }
}


fun main() {
    TrainingLineApp().start()
}







