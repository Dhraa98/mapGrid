package com.mapgrid.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapgrid.Coordinate
import com.networking.retrofit.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url


class CoordinateRepository(
    var TopLeftlat: Double,
    var TopLeftlng: Double,
    var BottomRightlat: Double,
    var BottomRightlng: Double
) {

   /* suspend fun getUsers() = RetrofitClass.getClient.getPlacesApi(
        "$TopLeftlat/$TopLeftlng/$BottomRightlat/$BottomRightlng"


    )*/
     var dataValue: MutableLiveData<Coordinate> = MutableLiveData()
     private  val TAG = "RestauratRepository"



     fun getPlaces(): LiveData<Coordinate> {

         val call: Call<Coordinate> =
             RetrofitClass.getClient.getPlacesApi("$TopLeftlat/$TopLeftlng/$BottomRightlat/$BottomRightlng")

         call.enqueue(object : Callback<Coordinate> {

             override fun onResponse(
                 call: Call<Coordinate>?,
                 response: Response<Coordinate>?
             ) {
                 if (response!!.isSuccessful) {


                     if (response.body()!!.cOORDINATEINFO!!.size != null && response.body()!!.cOORDINATEINFO!!.size > 0) {

                             dataValue.postValue( response.body())



                     }else{
                         Log.e(TAG, "onResponse: " )
                     }
                 }else{
                     Log.e(TAG, "onResponse:  "+response.message() )
                 }


             }

             override fun onFailure(call: Call<Coordinate>?, t: Throwable?) {
                 dataValue.postValue( null)
                 //  Toast.makeText(context, t!!.message, Toast.LENGTH_SHORT).show()
             }
         })
         return dataValue
     }
}