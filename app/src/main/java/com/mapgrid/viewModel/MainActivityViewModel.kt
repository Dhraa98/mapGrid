package com.mapgrid.viewModel

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mapgrid.Coordinate
import com.mapgrid.repository.CoordinateRepository
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import java.util.*

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    var TopLeftlatitude = 0.0
    var TopLeftlongitude = 0.0

    var BottomRightlatitude = 0.0
    var BottomRightlongitude = 0.0


    fun getRestaurant(): LiveData<Coordinate> {
        val userRepository = CoordinateRepository(TopLeftlatitude, TopLeftlongitude,BottomRightlatitude,BottomRightlongitude)
       val loginResponseModelMutableLiveData = userRepository.getPlaces()
        return loginResponseModelMutableLiveData
    }
    var progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)


    var itemClicked: MutableLiveData<Boolean> = MutableLiveData(false)
   /* fun getRestaurant() = liveData(Dispatchers.IO) {
        val userRepository = CoordinateRepository(TopLeftlatitude, TopLeftlongitude,BottomRightlatitude,BottomRightlongitude)
        val retrievedData = userRepository.getUsers()
        emit(retrievedData)

    }*/
/*
    val data: LiveData<Response<RestaurantModel>> = liveData(Dispatchers.IO) {
//        progressVisibility.value=true
        val userRepository = RestauratRepository(latitude, longitude)
        val retrievedData = userRepository.getUsers()
        emit(retrievedData)

//        progressVisibility.value=false

    }*/



}