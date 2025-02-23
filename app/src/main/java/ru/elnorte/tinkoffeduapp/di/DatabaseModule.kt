package ru.elnorte.tinkoffeduapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.elnorte.tinkoffeduapp.data.movierepository.database.FavDatabase
import ru.elnorte.tinkoffeduapp.data.movierepository.database.FavDatabaseDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): FavDatabase {
        return Room.databaseBuilder(
            context,
            FavDatabase::class.java,
            "favs_table"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavDatabaseDao(database: FavDatabase): FavDatabaseDao = database.favDatabaseDao
}
