package ru.elnorte.tinkoffeduapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.elnorte.data.movierepository.MovieRepository
import ru.elnorte.data.movierepository.MovieRepositoryImpl
import ru.elnorte.data.movierepository.TopMoviesResponseConverter
import ru.elnorte.data.movierepository.database.FavDatabase
import ru.elnorte.data.movierepository.database.FavDatabaseDao
import ru.elnorte.data.remote.MovieRemoteDataSource
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
    fun provideFavDatabaseDao(database: FavDatabase) = database.favDatabaseDao

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideRepository(
        dao: FavDatabaseDao,
        remoteDataSource: MovieRemoteDataSource,
        moviesResponseConverter: TopMoviesResponseConverter,
    ): MovieRepository {
        return MovieRepositoryImpl(dao, remoteDataSource, moviesResponseConverter)
    }
}
