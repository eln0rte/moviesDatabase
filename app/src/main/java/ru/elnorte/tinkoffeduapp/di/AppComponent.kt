package ru.elnorte.tinkoffeduapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.elnorte.tinkoffeduapp.ui.movieinfo.MovieFragment
import ru.elnorte.tinkoffeduapp.ui.overview.OverviewFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: OverviewFragment)
    fun inject(fragment: MovieFragment)
}
