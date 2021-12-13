package com.trainlingline.part_8_component_dependancies

import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject
import javax.inject.Named

@ScreenScope
@Component(
    modules = [TicketScreenModule::class, TicketRepoModule::class],
    dependencies = [AppComponent::class]
)
interface TicketScreenComponent {

    fun provideTicketScreenPresenter(): TicketsScreenContract.Presenter
}

@Module
interface TicketScreenModule {

    @Binds
    fun bindPresenter(impl: TicketsScreenPresenter): TicketsScreenContract.Presenter

    @Binds
    fun bindScreen(impl: TicketsScreen): TicketsScreenContract.Screen

}


interface TicketsScreenContract {

    interface Presenter {
        fun present()
    }

    interface Screen {
        fun show(userName: String, userId: String)
    }
}

class TicketsScreenPresenter @Inject constructor(
    @Named("userName") private val userName: String,
    @Named("userId") private val userId: String,
    private val ticketsRepo: TicketRepo,
    private val screen: TicketsScreenContract.Screen
) : TicketsScreenContract.Presenter {

    override fun present() {
        ticketsRepo.getTicketsForUser(userId)
        // Do some stuff
        screen.show(userName, userId)
    }
}

class TicketsScreen @Inject constructor() : TicketsScreenContract.Screen {

    override fun show(userName: String, userId: String) {
        print("Showing tickets for $userName $userId")
    }
}


