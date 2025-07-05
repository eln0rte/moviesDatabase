package ru.elnorte.data.impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.elnorte.data.api.movierepository.MovieRepository
import ru.elnorte.data.impl.movierepository.MovieLocalDataSource
import ru.elnorte.data.impl.movierepository.MovieRemoteDataSource
import ru.elnorte.data.impl.movierepository.MovieRepositoryImpl
import ru.elnorte.data.impl.movierepository.MoviesConverter
import ru.elnorte.data.impl.movierepository.local.database.FavDatabase
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
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource,
    ): MovieRepository {
        val converter = MoviesConverter()
        return MovieRepositoryImpl(
            localDataSource,
            remoteDataSource,
            converter
        )
    }
}
