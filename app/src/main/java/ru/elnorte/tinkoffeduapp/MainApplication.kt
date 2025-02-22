package ru.elnorte.tinkoffeduapp

import android.app.Application
import ru.elnorte.tinkoffeduapp.di.AppComponent
import ru.elnorte.tinkoffeduapp.di.DaggerAppComponent

class MainApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}
