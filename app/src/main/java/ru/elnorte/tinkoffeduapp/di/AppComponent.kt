package ru.elnorte.tinkoffeduapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.elnorte.features.di.FeatureComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class,FeatureModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun featureComponent(): FeatureComponent.Factory

}
