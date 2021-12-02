package com.trainlingline.part_5_simple_use_of_scopes

import dagger.Binds
import dagger.Module
import okhttp3.OkHttpClient
import javax.inject.Inject

@Module
interface UserRepoModule {

    @Binds
    fun bindRepo(impl: RemoteUserRepo): UserRepo

}

interface UserRepo {
    fun getUser(userId: String): User
}

data class User(val name: String)

class RemoteUserRepo @Inject constructor(
    private val okHttpClient: OkHttpClient
) : UserRepo {

    init {
        println("okHttpClient = $okHttpClient")
    }

    override fun getUser(userId: String): User {
        // non-sense okhttp call so that it is used
        okHttpClient.cache
        return User("User")
    }
}





