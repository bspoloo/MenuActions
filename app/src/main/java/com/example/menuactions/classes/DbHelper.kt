package com.example.menuactions.classes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.menuactions.dataclasses.Lugar

class DbHelper (context : Context) :

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {

        var  createTable = "create table $TABLE_NAME (" +
                "$COLUMN_ID integer primary key AUTOINCREMENT," +
                "$COLUMN_NOMBRE varchar(20) not null,"+
                "$COLUMN_DESCRIPCION varchar(50) not null,"+
                "$COLUMN_LATITUD real not null,"+
                "$COLUMN_LONGITUD real not null)"
        db?.execSQL(createTable);
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    public fun insertLugar(lugar : Lugar) : Long{
        val db = this.writableDatabase;
        val values = ContentValues();
        values.put(COLUMN_NOMBRE, lugar.nombre);
        values.put(COLUMN_DESCRIPCION, lugar.descripcion);
        values.put(COLUMN_LATITUD, lugar.latitud);
        values.put(COLUMN_LONGITUD, lugar.longitud);

        return db.insert(TABLE_NAME, null, values)
    }
    fun updateLugar(lugar: Lugar): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, lugar.nombre)
        values.put(COLUMN_DESCRIPCION, lugar.descripcion)
        values.put(COLUMN_LATITUD, lugar.latitud)
        values.put(COLUMN_LONGITUD, lugar.longitud)

        return db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(lugar.id.toString())
        )
    }

    fun deleteLugar(id: Long) : Int{
        val db = this.writableDatabase;
        val data = db.delete(
            TABLE_NAME,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()))

        return data;
    }
    fun listLugar() : String{
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null, // todas las columnas
            null, null, null, null, null
        )

        val sb = StringBuilder()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUD))
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUD))

                sb.append("ID: $id | $nombre - $descripcion (Lat: $latitud, Lng: $longitud)\n")
            } while (cursor.moveToNext())
        }

        cursor.close()

        return sb.toString()
    }

    companion object{
        const val DATABASE_NAME = "lugares.db";
        const val DATABASE_VERSION = 1;
        const val TABLE_NAME = "lugares";
        const val COLUMN_ID = "id";
        const val COLUMN_NOMBRE = "nombre";
        const val COLUMN_DESCRIPCION = "descripcion";
        const val COLUMN_LATITUD = "latitud";
        const val COLUMN_LONGITUD = "longitud";
    }
}