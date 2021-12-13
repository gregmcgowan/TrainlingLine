package com.trainlingline.part_10_multibindings

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Named


@Module
interface HelperIntoSetModule {

    @Binds
    @IntoSet
    @Named("map")
    fun bindGoodHelper(goodHelper: GoodHelper): Helper

    @Binds
    @IntoSet
    @Named("map")
    fun bindEvenBetterHelper(evenBetterHelper: EvenBetterHelper): Helper

}


@Module
class HelperIntoSetModuleProvides {

    @Provides
    @IntoSet
    fun provideGoodHelper(): Helper = GoodHelper()

    @Provides
    @IntoSet
    fun provideEvenBetterHelper(): Helper = EvenBetterHelper()

}


@Module
class HelperElementsIntoSetModule {

    @Provides
    @ElementsIntoSet
    fun provideHelpers(goodHelper: GoodHelper, evenBetterHelper: EvenBetterHelper): Set<Helper> =
        setOf(goodHelper, evenBetterHelper)


}

