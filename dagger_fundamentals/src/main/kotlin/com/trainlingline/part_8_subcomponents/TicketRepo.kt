package com.trainlingline.part_8_subcomponents

import dagger.Binds
import dagger.Module
import okhttp3.OkHttpClient
import javax.inject.Inject

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


@Module
interface TicketRepoModule {

    @Binds
    fun bindRepo(impl: RemoteTicketRepo): TicketRepo

}