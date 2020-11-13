package com.trainlingline.hustle

import dagger.MapKey
import javax.inject.Inject
import kotlin.reflect.KClass


object InjectionOfHustle {

    fun inject(hustle: Hustle) {
        (hustle.app as HasHustleInjector<Any>).injector().inject(hustle)
    }
}

interface HustleInjector<H> {

    fun inject(hustle: H)

    interface Factory<H : Any> {
        fun build(): HustleInjector<H>
    }
}

interface HasHustleInjector<H : Any> {
    fun injector(): HustleInjector<H>
}


class HustleInjectorDispatcher<H : Any> @Inject constructor(
    private val factoryMap: Map<Class<*>, @JvmSuppressWildcards HustleInjector.Factory<*>>
) : HustleInjector<H> {


    override fun inject(hustle: H) {
        val factory: HustleInjector.Factory<*>? = factoryMap[hustle::class.java]
        (factory as HustleInjector.Factory<H>).build().inject(hustle)
    }
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class HustleKey(val value: KClass<*>)





