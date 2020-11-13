package com.trainlingline.hustle

import dagger.*
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UserRepoModule::class,
        NetworkModule::class,
        AppSubComponentBuilders::class
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

@Module
interface AppSubComponentBuilders {

    @Binds
    @IntoMap
    @HustleKey(HomeHustle::class)
    fun bindHomeBuilder(impl: HomeScreenSubcomponent.Builder): HustleInjector.Factory<*>

    @Binds
    @IntoMap
    @HustleKey(TicketHustle::class)
    fun bindTicketBuilder(impl: TicketScreenSubcomponent.Builder): HustleInjector.Factory<*>

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

class TrainingLineApp : HasHustleInjector<Any>, Navigator, HustleApp() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var injector: HustleInjectorDispatcher<Any>

    fun start() {
        appComponent = DaggerAppComponent
            .builder()
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

    override fun injector(): HustleInjector<Any> = injector
}


fun main() {
    val trainingLineApp = TrainingLineApp()
    trainingLineApp.start()
    trainingLineApp.showHomeScreen()
    trainingLineApp.showTickets()
}









