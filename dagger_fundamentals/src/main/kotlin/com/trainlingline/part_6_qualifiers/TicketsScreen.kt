package com.trainlingline.part_6_qualifiers

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier

interface TicketsScreenContract {

    interface Presenter {
        fun present()
    }

    interface Screen {
        fun show(userName: String, userId: String)
    }
}

class TicketsScreenPresenter @Inject constructor(
    private val userName: String,
    private val userId: String,
    private val ticketsRepo: TicketRepo,
    private val screen: TicketsScreenContract.Screen
) : TicketsScreenContract.Presenter {

    override fun present() {
        ticketsRepo.getTicketsForUser(userId)
        // Do some stuff
        screen.show(userName, userId)
    }
}

class TicketsScreen @Inject constructor() :
    TicketsScreenContract.Screen {

    override fun show(userName: String, userId: String) {
        print("Showing tickets for $userName $userId")
    }
}

@Qualifier
annotation class UserCategoryType(val userCategory : UserCategory)

enum class UserCategory{
    GUEST, LOGGEDIN
}


@Module(includes = [TicketScreenModule.Bindings::class])
class TicketScreenModule(
    private val userName: String,
    private val userId: String
) {


    @Provides
    fun provideUserId(): String = userId

    @Module
    interface Bindings {

        @Binds
        fun bindPresenter(impl: TicketsScreenPresenter): TicketsScreenContract.Presenter

        @Binds
        fun bindScreen(impl: TicketsScreen): TicketsScreenContract.Screen
    }


}