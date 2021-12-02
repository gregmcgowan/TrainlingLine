package com.trainlingline.part_5_simple_use_of_scopes

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

    init {
        println("okHttpClient = $okHttpClient")
    }

    override fun getTicketsForUser(userID: String): List<Ticket> {
        // non-sense okhttp call so that it is used
        okHttpClient.cache
        return listOf(Ticket("return"))
    }
}


data class Ticket(val ticketName: String)


