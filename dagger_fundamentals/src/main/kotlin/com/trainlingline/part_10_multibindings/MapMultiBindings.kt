package com.trainlingline.part_10_multibindings

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey


@Module
interface MapMultiBindingsSimpleKeys {

    @Binds
    @IntoMap
    @StringKey("Good")
    fun goodHelper(goodHelper: GoodHelper): Helper


    @Binds
    @IntoMap
    @StringKey("evenBetter")
    fun evenBetterHelper(evenBetterHelper: EvenBetterHelper): Helper
}


@Module
interface MapMultiBindingsComplexKeys {

    @Binds
    @IntoMap
    @HelperKey(HelperType.GOOD)
    fun goodHelper(goodHelper: GoodHelper): Helper


    @Binds
    @IntoMap
    @HelperKey(HelperType.EVEN_BETTER)
    fun evenBetterHelper(evenBetterHelper: EvenBetterHelper): Helper
}

enum class HelperType {
    GOOD, EVEN_BETTER
}


@MapKey
annotation class HelperKey(val value: HelperType)
