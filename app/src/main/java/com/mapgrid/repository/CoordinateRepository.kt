package com.mapgrid.repository


import com.networking.retrofit.RetrofitClass
import retrofit2.http.Url


class CoordinateRepository(
    var TopLeftlat: Double,
    var TopLeftlng: Double,
    var BottomRightlat: Double,
    var BottomRightlng: Double
) {

    suspend fun getUsers() = RetrofitClass.getClient.getPlacesApi(
        "$TopLeftlat/$TopLeftlng/$BottomRightlat/$BottomRightlng"


    )
    /* var dataValue: MutableLiveData<RestaurantModel> = MutableLiveData()
     private  val TAG = "RestauratRepository"



     fun getPlaces(): LiveData<RestaurantModel> {

         val call: Call<RestaurantModel> =
             RetrofitClass.getClient.getPlacesApi("1cc5f7df7bf1425e718a4ca9c504cada",
                 lat,
                 lng)

         call.enqueue(object : Callback<RestaurantModel> {

             override fun onResponse(
                 call: Call<RestaurantModel>?,
                 response: Response<RestaurantModel>?
             ) {
                 if (response!!.isSuccessful) {


                     if (response.body()!!.nearbyRestaurants!!.size != null && response.body()!!.nearbyRestaurants!!.size > 0) {

                             dataValue.postValue( response.body())



                     }else{
                         Log.e(TAG, "onResponse: " )
                     }
                 }else{
                     Log.e(TAG, "onResponse:  "+response.message() )
                 }


             }

             override fun onFailure(call: Call<RestaurantModel>?, t: Throwable?) {
                 dataValue.postValue( null)
                 //  Toast.makeText(context, t!!.message, Toast.LENGTH_SHORT).show()
             }
         })
         return dataValue
     }*/
}