package ru.elnorte.tinkoffeduapp.data.movierepository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.elnorte.tinkoffeduapp.data.models.FavDatabaseModel

@Database(entities = [FavDatabaseModel::class], version = 1, exportSchema = false)
abstract class FavDatabase :RoomDatabase() {
    abstract val favDatabaseDao: FavDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: FavDatabase? = null

        fun getInstance(context: Context): FavDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavDatabase::class.java,
                        "favs_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}