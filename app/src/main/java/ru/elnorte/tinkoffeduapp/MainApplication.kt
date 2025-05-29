package ru.elnorte.tinkoffeduapp

import android.app.Application
import ru.elnorte.features.di.FeatureComponent
import ru.elnorte.features.di.FeatureComponentProvider
import ru.elnorte.tinkoffeduapp.di.AppComponent
import ru.elnorte.tinkoffeduapp.di.DaggerAppComponent

class MainApplication : Application(), FeatureComponentProvider {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun provideFeatureComponent(): FeatureComponent {
        return appComponent.featureComponentFactory().create(this)
    }
}
