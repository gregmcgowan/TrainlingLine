package com.trainlingline.part_8_component_dependancies

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Named
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
            .userId("1")
            .userUsername("greg")
            .build()
    }


    fun showHomeScreen() {
        DaggerHomeScreenComponent
            .builder()
            .appComponent(appComponent)
            .build()
            .provideHomeScreenPresenter()
            .present()
    }

    fun showTickets() {
        DaggerTicketScreenComponent
            .builder()
            .appComponent(appComponent)
            .build()
            .provideTicketScreenPresenter()
            .present()
    }
}


@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun okHttp(): OkHttpClient

    @Named("userId")
    fun userID(): String

    @Named("userName")
    fun userName(): String

    fun inject(app: TrainingLineApp)

    @Component.Builder
    interface Builder {

        fun userId(@BindsInstance @Named("userId") userId: String): Builder
        fun userUsername(@BindsInstance @Named("userName") userName: String): Builder
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


interface OtherComponent {
    fun getSomething(): Int
}

@ViewScope
@Component(modules = [OtherModule::class])
interface OtherComponentImpl : OtherComponent {

}

@Module
class OtherModule {

    @Provides
    fun somethingValue(): Int = 1
}













