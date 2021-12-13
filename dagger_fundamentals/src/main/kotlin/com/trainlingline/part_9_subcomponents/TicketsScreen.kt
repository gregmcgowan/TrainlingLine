package com.trainlingline.part_9_subcomponents

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

@ScreenScope
@Subcomponent(modules = [TicketScreenModule::class, TicketRepoModule::class])
interface TicketScreenSubcomponent {


    fun ticketScreenPresenter(): TicketsScreenContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        fun build(): TicketScreenSubcomponent
    }
}

@Module(subcomponents = [TicketListSubcomponent::class])
interface TicketScreenModule {

    @Binds
    fun bindPresenter(impl: TicketsScreenPresenter): TicketsScreenContract.Presenter

    @Binds
    fun bindScreen(impl: TicketsScreen): TicketsScreenContract.Screen

}

@ViewScope
@Subcomponent(modules = [TicketScreenListModule::class])
interface TicketListSubcomponent {

    fun ticketScreenListPresenter(): TicketScreenListContract.Presenter

    @Subcomponent.Builder
    interface Builder {
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

}

interface TicketScreenListContract {

    interface View {
        fun showTickets(tickets: List<Ticket>)
    }

    interface Presenter {
        fun present()
    }
}

class TicketsScreenPresenter @Inject constructor(
    private val ticketsRepo: TicketRepo,
    private val screen: TicketsScreenContract.Screen,
    private val ticketListBuilder: TicketListSubcomponent.Builder
) : TicketsScreenContract.Presenter {

    override fun present() {
        // show some loading perhaps
        screen.show()

        // but we need the tickets
        ticketsRepo.getTicketsForUser("1")

        // now later a list is needed
        // and we can create it
        ticketListBuilder
            .build()
            .ticketScreenListPresenter()
            .apply { present() }
    }
}

@ScreenScope
class TicketsScreen @Inject constructor() : TicketsScreenContract.Screen {

    override fun show() {
        println("Showing TicketsScreen")
    }

}


class TicketScreenListPresenter @Inject constructor(
    private val view: TicketScreenListContract.View
) : TicketScreenListContract.Presenter {


    override fun present() {
        view.showTickets(emptyList())
    }
}


class TicketScreenListView @Inject constructor() : TicketScreenListContract.View {

    override fun showTickets(tickets: List<Ticket>) {
        print("Showing ticket list")
    }
}







