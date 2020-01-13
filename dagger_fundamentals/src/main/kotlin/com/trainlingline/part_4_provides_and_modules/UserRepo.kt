package com.trainlingline.part_4_provides_and_modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject

interface UserRepo {
    fun getUser(userId: String): User
}

data class User(val name: String)



class RemoteUserRepo @Inject constructor(
    private val okHttpClient: OkHttpClient
) : UserRepo {

    override fun getUser(userId: String): User {
        // Pretend we have some okhttp code here
        return User("User")
    }
}


@Module
interface UserRepoModule {

    @Binds
    fun bindRepo(impl: RemoteUserRepo): UserRepo

}

@Module
class NetworkModule {

    @Provides
    fun provideOkHttp() = OkHttpClient()

}