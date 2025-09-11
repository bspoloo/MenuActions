package com.example.menuactions.interfaces

import com.example.menuactions.dataclasses.Album
import retrofit2.Call //use of retrofit
import retrofit2.http.GET

interface IAPIServices  {
    @GET("albums")
    fun listAlbums(): Call<List<Album>>
}