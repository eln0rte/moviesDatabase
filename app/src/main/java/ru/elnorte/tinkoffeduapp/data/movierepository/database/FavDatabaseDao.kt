package ru.elnorte.tinkoffeduapp.data.movierepository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.elnorte.tinkoffeduapp.data.models.FavDatabaseModel

@Dao
interface FavDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavDatabaseModel)

    @Update
    suspend fun update(item: FavDatabaseModel)
    @Query("DELETE FROM favs_table WHERE id = :movieId")
    suspend fun delete(movieId: Int)

    @Query("SELECT * from favs_table WHERE id = :movieId")
    suspend fun get(movieId: Int): FavDatabaseModel

    @Query("SELECT CASE WHEN EXISTS (SELECT * from favs_table WHERE id = :movieId) THEN CAST (1 AS BIT) ELSE CAST(0 AS BIT) END")
    suspend fun checkIfExists(movieId: Int): Boolean

    @Query("SELECT * from favs_table ")
    suspend fun getAll(): List<FavDatabaseModel>
}