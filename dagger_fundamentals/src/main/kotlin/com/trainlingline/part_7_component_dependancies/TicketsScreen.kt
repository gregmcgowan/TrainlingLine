package com.trainlingline.part_7_component_dependancies

import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject

interface TicketsScreenContract {

    interface Presenter {
        fun present()
    }

    interface Screen {
        fun show()
    }
}

class TicketsScreenPresenter @Inject constructor(
    private val ticketsRepo: TicketRepo,
    private val screen: TicketsScreenContract.Screen
) : TicketsScreenContract.Presenter {

    override fun present() {
        ticketsRepo.getTicketsForUser("1")
        // Do some stuff
        screen.show()
    }
}

class TicketsScreen @Inject constructor() : TicketsScreenContract.Screen {

    override fun show() {
        print("Showing tickets")
    }
}

@Module
interface TicketScreenModule {

    @Binds
    fun bindPresenter(impl: TicketsScreenPresenter): TicketsScreenContract.Presenter

    @Binds
    fun bindScreen(impl: TicketsScreen): TicketsScreenContract.Screen

}

@ScreenScope
@Component(
    modules = [TicketScreenModule::class, TicketRepoModule::class],
    dependencies = [AppComponent::class]
)
interface TicketScreenComponent {
    fun provideTicketScreenPresenter(): TicketsScreenContract.Presenter
}