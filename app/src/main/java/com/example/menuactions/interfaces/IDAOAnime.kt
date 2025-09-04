package com.example.menuactions.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.menuactions.Entities.Anime

@Dao
interface IDAOAnime {
    @Query("SELECT * FROM animes")
    suspend fun getAllAnime(): MutableList<Anime>

    @Query("SELECT * FROM animes WHERE id = :id")
    suspend fun getAnimeById(id: Int): Anime

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnime(anime: Anime)

    @Query("UPDATE animes SET name=:name, urlImage=:urlImage, totalCaps=:totalCaps, type=:type WHERE id=:id")
    suspend fun updateAnimeById(id:Int, name:String, urlImage:String, totalCaps:Int, type: String)

    @Query("DELETE FROM animes WHERE id = :id")
    suspend fun deleteAnimeById(id: Int)
}