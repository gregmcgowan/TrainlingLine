package com.trainlingline.part_6_qualifiers

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier


@Qualifier
annotation class CustomQualifier(val custom: Custom)

enum class Custom {
    USER_ID, USER_NAME
}


@Module(includes = [TicketScreenModule.Bindings::class])
class TicketScreenModule(
    private val userName: String,
    private val userId: String
) {


    @Provides
    @Named("USER_ID")
    fun provideUserId(): String = userId

    @Provides
    @Named("USER_NAME")
    fun provideUseName(): String = userName

    @Module
    interface Bindings {

        @Binds
        fun bindPresenter(impl: TicketsScreenPresenter): TicketsScreenContract.Presenter

        @Binds
        fun bindScreen(impl: TicketsScreen): TicketsScreenContract.Screen
    }


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
    @Named("USER_NAME") private val userName: String,
    @Named("USER_ID") private val userId: String,
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

