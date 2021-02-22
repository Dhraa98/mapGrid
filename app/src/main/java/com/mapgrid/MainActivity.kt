package com.mapgrid

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.mapgrid.viewModel.MainActivityViewModel
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraChangeListener {
    private lateinit var map: GoogleMap
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var polygon: Polygon


    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var list: ArrayList<LatLng> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initMap()

    }

    private fun initMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            map = p0
        }

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        //map.animateCamera(CameraUpdateFactory.zoomTo(21.0f));

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                p0?.apply {
                    val currentLocation = LatLng(latitude, longitude)
                    addMarker(
                        MarkerOptions()
                            .position(currentLocation)
                            .title("currentLocation")
                    )
                    p0?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                latitude,
                                longitude
                            ), 20f
                        )
                    )
                }
            }
        }
        p0?.setOnCameraChangeListener(this);


    }


    override fun onCameraChange(p0: CameraPosition?) {
        val zoomLevel: Float = p0!!.zoom

        val visibleRegion: VisibleRegion = map.getProjection().getVisibleRegion()
        val nearLeft = visibleRegion.nearLeft
        val nearRight = visibleRegion.nearRight
        val farLeft = visibleRegion.farLeft
        val farRight = visibleRegion.farRight
        viewModel.TopLeftlatitude = nearLeft.latitude
        viewModel.TopLeftlongitude = nearLeft.longitude
        viewModel.BottomRightlatitude = farRight.latitude
        viewModel.BottomRightlongitude = farRight.longitude



        Log.e(
            "TAG",
            "onCameraChange: " + nearLeft + "==" + nearRight + "==" + farLeft + "==" + farRight
        )
        Log.e("TAG", "ZoomLevel: " + zoomLevel)


        //  val geoJsonData: JSONObject?
        if (zoomLevel >= 19f) {

            //  list.clear()
            viewModel.getRestaurant().observe(this, androidx.lifecycle.Observer {


//                if (it.isSuccessful) {
                // Log.e("TAG", "onCameraChange: "+it.body() )

                for (i in it.cOORDINATEINFO!!.indices) {

                    //list.addAll(listOf(it.body()!!.cOORDINATEINFO!![i]))
                    val rectOptions = PolygonOptions()
                        .add(
                            LatLng(
                                it.cOORDINATEINFO!![i].cOORDINATE1!![0].toDouble(),
                                it.cOORDINATEINFO!![i].cOORDINATE1!![1].toDouble()
                            ),
                            LatLng(
                                it.cOORDINATEINFO!![i].cOORDINATE2!![0].toDouble(),
                                it.cOORDINATEINFO!![i].cOORDINATE2!![1].toDouble()
                            ),
                            LatLng(
                                it.cOORDINATEINFO!![i].cOORDINATE3!![0].toDouble(),
                                it.cOORDINATEINFO!![i].cOORDINATE3!![1].toDouble()
                            ),
                            LatLng(
                                it.cOORDINATEINFO!![i].cOORDINATE4!![0].toDouble(),
                                it.cOORDINATEINFO!![i].cOORDINATE4!![1].toDouble()
                            ),
                            LatLng(
                                it.cOORDINATEINFO!![i].cOORDINATE5!![0].toDouble(),
                                it.cOORDINATEINFO!![i].cOORDINATE5!![1].toDouble()
                            )
                        )
                        .strokeColor(Color.CYAN)
                        .strokeWidth(2f)
                        .clickable(true)

                    polygon = map.addPolygon(rectOptions)



                    if (i == 1000) {
                        break
                    }


                }


                /*  val featureCollection = JSONObject()
                  try {
                      featureCollection.put("type", "FeatureCollection")
                      val featureList = JSONArray()

                      for (obj in list) {

                          val point = JSONObject()
                          point.put("type", "Polygon")

                          val coord =
                              JSONArray("[[" + "["+obj.cOORDINATE1!!.get(1)+ "," +obj.cOORDINATE1!!.get(0) + "],[" + obj.cOORDINATE2!!.get(1)+ "," +obj.cOORDINATE2!!.get(0) + "],[" + obj.cOORDINATE3!!.get(1)+ "," +obj.cOORDINATE3!!.get(0) + "],[" + obj.cOORDINATE4!!.get(1)+ "," +obj.cOORDINATE4!!.get(0) + "],[" + obj.cOORDINATE5!!.get(1)+ "," +obj.cOORDINATE5!!.get(0) + "]]]")
                          point.put("coordinates", coord)
                          val feature = JSONObject()
                          feature.put("type", "Feature")
                          feature.put("geometry", point)
                          featureList.put(feature)
                          featureCollection.put("features", featureList)
                      }
                  } catch (e: JSONException) {
                      Log.e("TAG", "onCreate: $e")

                  }

                  Log.e("TAG", featureCollection.toString())

                  createGrid(featureCollection)*/
                // createRectangle()


            })

        } else if (zoomLevel <= 19) {
          //  polygon.remove()
            // polygon = map.addPolygon(null)
            //  map.clear()

        }


        map.setOnPolygonClickListener(object : GoogleMap.OnPolygonClickListener {
            override fun onPolygonClick(p0: Polygon?) {

                if (p0 != null) {
                    polygon = p0

                }
                p0!!.fillColor = Color.BLUE
                Log.e("TAG", "onPolygonClick: "+p0.id )
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            p0.points[0].latitude,
                            p0.points[0].longitude
                        ), 20f
                    )
                )
            }

        })


    }





    }




