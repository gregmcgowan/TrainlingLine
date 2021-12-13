package com.trainlingline.part_8_component_dependancies

import dagger.Binds
import dagger.Module
import okhttp3.OkHttpClient
import javax.inject.Inject

@Module
interface TicketRepoModule {

    @Binds
    fun bindRepo(impl: RemoteTicketRepo): TicketRepo

}

interface TicketRepo {
    fun getTicketsForUser(userID: String): List<Ticket>
}


class RemoteTicketRepo @Inject constructor(
    private val okHttpClient: OkHttpClient
) : TicketRepo {

    override fun getTicketsForUser(userID: String): List<Ticket> {
        // do something with ok http

        return listOf(Ticket("return"))
    }
}


data class Ticket(val ticketName: String)




