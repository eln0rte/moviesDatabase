package ru.elnorte.data.impl.movierepository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.elnorte.data.impl.movierepository.models.FavDatabaseModel

@Database(entities = [FavDatabaseModel::class], version = 1, exportSchema = false)
abstract class FavDatabase :RoomDatabase() {
    abstract val favDatabaseDao: FavDatabaseDao
}
