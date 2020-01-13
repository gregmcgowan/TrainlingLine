package com.trainlingline.part_8_subcomponents

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
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
@Subcomponent(modules = [TicketScreenModule::class, TicketRepoModule::class])
interface TicketScreenSubComponent {
    fun provideTicketScreenPresenter(): TicketsScreenContract.Presenter

    @Subcomponent.Builder
    interface Builder {

        fun build(): TicketScreenSubComponent
    }
}