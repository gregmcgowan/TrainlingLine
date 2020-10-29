package com.trainlingline.part_11_dagger_traindroid

import dagger.MapKey
import javax.inject.Inject
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class HustleKey(val value: KClass<*>)

object InjectionOfHustle {

    fun inject(hustle: Hustle) {
        (hustle.app as HasHustleInjector<Any>).injector().inject(hustle)
    }
}

class HustleInjectorDispatcher<H : Any> @Inject constructor(
    private val factoryMap: Map<Class<*>, @JvmSuppressWildcards HustleInjector.Factory<*>>
) : HustleInjector<H> {


    override fun inject(hustle: H) {
        val factory: HustleInjector.Factory<*>? = factoryMap[hustle::class.java]
        (factory as HustleInjector.Factory<H>).build().inject(hustle)
    }
}

interface HasHustleInjector<H : Any> {
    fun injector(): HustleInjector<H>
}


interface HustleInjector<H> {

    fun inject(hustle: H)

    interface Factory<H : Any> {
        fun build(): HustleInjector<H>
    }
}