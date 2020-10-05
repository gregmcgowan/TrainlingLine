package com.trainlingline.part_10_multibindings

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

@ScreenScope
@Subcomponent(modules = [TicketScreenModule::class, TicketRepoModule::class])
interface TicketScreenSubcomponent {

    fun ticketScreenPresenter(): TicketsScreenContract.Presenter

    @Subcomponent.Factory
    interface Builder {
        fun build(): TicketScreenSubcomponent
    }
}

@Module(subcomponents = [TicketListSubcomponent::class])
interface TicketScreenModule {

    @Binds
    fun bindPresenter(impl: TicketsScreenPresenter): TicketsScreenContract.Presenter

    @Binds
    @ScreenScope
    fun bindScreen(impl: TicketsScreen): TicketsScreenContract.Screen

    @Binds
    @ScreenScope
    fun bindInteractions(impl: TicketsScreen): TicketsScreenContract.Interactions

}

@ViewScope
@Subcomponent(modules = [TicketScreenListModule::class])
interface TicketListSubcomponent {

    fun interactions(): TicketsScreenContract.Interactions
    fun ticketScreenListPresenter(): TicketScreenListContract.Presenter

    @Subcomponent.Factory
    interface Factory {
        fun build(): TicketListSubcomponent
    }
}

@Module
interface TicketScreenListModule {

    @Binds
    fun bindPresenter(impl: TicketScreenListPresenter): TicketScreenListContract.Presenter

    @Binds
    fun bindView(impl: TicketScreenListView): TicketScreenListContract.View
}


interface TicketsScreenContract {

    interface Presenter {
        fun present()
    }

    interface Screen {
        fun show()
    }

    interface Interactions {
        fun something()
    }
}

interface TicketScreenListContract {

    interface View {
        fun showTickets(tickets: List<Ticket>)
    }

    interface Presenter {
        fun init(interactions: TicketsScreenContract.Interactions)
        fun present()
    }
}

class TicketsScreenPresenter @Inject constructor(
    private val ticketsRepo: TicketRepo,
    private val screen: TicketsScreenContract.Screen,
    private val ticketListBuilder: TicketListSubcomponent.Factory
) : TicketsScreenContract.Presenter {

    override fun present() {
        // show some loading perhaps
        screen.show()

        // but we need the tickets
        ticketsRepo.getTicketsForUser("1")

        // now later a list is needed
        // and we can create it
        val build = ticketListBuilder
            .build()

        build.ticketScreenListPresenter().present()
        build.interactions().something()


    }
}

@ScreenScope
class TicketsScreen @Inject constructor() : TicketsScreenContract.Screen,
    TicketsScreenContract.Interactions {

    override fun show() {
        println("Showing TicketsScreen " + this)
    }

    override fun something() {
        println("doing something " + this)
    }
}


class TicketScreenListPresenter @Inject constructor(
    private val view: TicketScreenListContract.View
) : TicketScreenListContract.Presenter {

    override fun init(interactions: TicketsScreenContract.Interactions) {
        interactions.something()
    }

    override fun present() {
        view.showTickets(emptyList())
    }
}


class TicketScreenListView @Inject constructor() :
    TicketScreenListContract.View {

    override fun showTickets(tickets: List<Ticket>) {
        print("Showing ticket list")
    }
}







