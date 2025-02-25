package ru.elnorte.tinkoffeduapp.data.movierepository.database

import androidx.room.Dao
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

    @Query("SELECT * FROM favs_table WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): FavDatabaseModel?

    @Query("SELECT id FROM favs_table")
    suspend fun getIds() : List<Int>

    @Query("SELECT * FROM favs_table ")
    suspend fun getAll(): List<FavDatabaseModel>
}
