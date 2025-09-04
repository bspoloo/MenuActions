package com.example.menuactions.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.menuactions.Entities.Lugar
import com.example.menuactions.interfaces.IDAOLugar

@Database(entities = [Lugar::class], version = 1, exportSchema = false)
abstract class DaoLugar: RoomDatabase() {
    abstract fun daoLugar(): IDAOLugar
}