package ru.elnorte.data.movierepository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.elnorte.core.models.FavDatabaseModel

@Database(entities = [FavDatabaseModel::class], version = 1, exportSchema = false)
abstract class FavDatabase :RoomDatabase() {
    abstract val favDatabaseDao: FavDatabaseDao
}
