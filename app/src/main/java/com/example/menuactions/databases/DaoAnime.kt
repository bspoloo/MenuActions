package com.example.menuactions.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.menuactions.Entities.Anime
import com.example.menuactions.interfaces.IDAOAnime

@Database(entities = [Anime::class], version = 1, exportSchema = true)
abstract class DaoAnime : RoomDatabase(){
    abstract fun daoAnime() : IDAOAnime
}