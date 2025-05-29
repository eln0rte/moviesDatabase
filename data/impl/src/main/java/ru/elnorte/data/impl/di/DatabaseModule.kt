package ru.elnorte.data.impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.elnorte.data.api.movierepository.MovieRepository
import ru.elnorte.data.impl.movierepository.MovieRepositoryImpl
import ru.elnorte.data.impl.movierepository.TopMoviesResponseConverter
import ru.elnorte.data.impl.movierepository.database.FavDatabase
import ru.elnorte.data.impl.movierepository.database.FavDatabaseDao
import ru.elnorte.data.impl.movierepository.MovieRemoteDataSource
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
    ): MovieRepository {
        val converter = TopMoviesResponseConverter()
        return MovieRepositoryImpl(
            dao,
            remoteDataSource,
            converter
        )
    }
}
