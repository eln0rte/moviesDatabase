package ru.elnorte.features.di

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import ru.elnorte.features.ui.movieinfo.MovieFragment
import ru.elnorte.features.ui.overview.OverviewFragment

@Subcomponent
interface FeatureComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): FeatureComponent
    }

    fun inject(fragment: OverviewFragment)
    fun inject(fragment: MovieFragment)
}
