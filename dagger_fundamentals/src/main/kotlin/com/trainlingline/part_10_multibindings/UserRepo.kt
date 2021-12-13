package com.trainlingline.part_10_multibindings

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

    override fun getUser(userId: String): User {
        return User("User")
    }
}


