package com.example.menuactions.interfaces

import com.example.menuactions.dataclasses.Album
import com.example.menuactions.dataclasses.Product
import com.example.menuactions.dto.ProductDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call //use of retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface IAPIServices  {
    @GET("albums")
    fun listAlbums(): Call<List<Album>>

    @GET("products")
    fun getAllProductsReactive() : Observable<List<Product>>

    @GET("products")
    fun getAllProducts() : Call<List<Product>>

    @POST("products")
    fun insertProduct(@Body product: ProductDto) : Call<Product>

    @PATCH("products/{id}")
    fun updateProduct(@Path("id") id : Int, @Body product: ProductDto) : Call<Product>

    @DELETE("products/{id}")
    fun deleteProduct(@Path("id") id : Int) : Call<Int>
}