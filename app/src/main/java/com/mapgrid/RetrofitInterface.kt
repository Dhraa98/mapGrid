package com.networking.retrofit


import com.mapgrid.Coordinate
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitInterface {


    //get Videos
//    @Headers("user-key : ${R.string.header}")
   /* @GET("/{query}")
    suspend fun getPlacesApi(
        @Path("query") topRight: String


    ): Response<Coordinate>*/

    @GET("/{query}")
     fun getPlacesApi(
        @Path("query") topRight: String


    ): Call<Coordinate>



}