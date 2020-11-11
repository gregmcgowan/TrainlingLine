package com.trainlingline.hustle

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

class TicketHustle : Hustle() {

    @Inject
    lateinit var ticketScreenPresenter: TicketsScreenContract.Presenter

    override fun start() {
        //TODO implement this
        InjectionOfHustle.inject(this)

        //TODO remove this
        ((app as TrainingLineApp).appComponent)
            .providerTicketScreenBuilder()
            .build()
            .inject(this)

        ticketScreenPresenter.present()
    }

    override fun stop() {
        ticketScreenPresenter.stop()
    }
}

@ScreenScope
@Subcomponent(modules = [TicketScreenModule::class, TicketRepoModule::class])
interface TicketScreenSubcomponent {

    fun inject(hustle: TicketHustle)

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
        fun stop()
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
    private val ticketListBuilder: TicketListSubcomponent.Builder
) : TicketsScreenContract.Presenter {

    override fun present() {
        // show some loading perhaps
        screen.show()

        // but we need the tickets
        ticketsRepo.getTicketsForUser("1")

        // now later a list is needed
        // and we can create it
        val builder = ticketListBuilder.build()
        with(builder) {
            val interactions = interactions();
            ticketScreenListPresenter()
                .apply {
                    init(interactions)
                    present()
                }
        }
    }

    override fun stop() {

    }
}

class TicketsScreen @Inject constructor() : TicketsScreenContract.Screen,
    TicketsScreenContract.Interactions {

    override fun show() {
        println("Showing TicketsScreen : $this")
    }

    override fun something() {
        println("Doing something with TicketsScreen $this")
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







