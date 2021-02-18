package com.mapgrid

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coordinate {
    @SerializedName("COORDINATE_INFO")
    @Expose
    var cOORDINATEINFO: List<COORDINATEINFO>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var status = 0
    class COORDINATEINFO {
        @SerializedName("COORDINATE_1")
        @Expose
        var cOORDINATE1: List<Float>? = null

        @SerializedName("COORDINATE_2")
        @Expose
        var cOORDINATE2: List<Float>? = null

        @SerializedName("COORDINATE_3")
        @Expose
        var cOORDINATE3: List<Float>? = null

        @SerializedName("COORDINATE_4")
        @Expose
        var cOORDINATE4: List<Float>? = null

        @SerializedName("COORDINATE_5")
        @Expose
        var cOORDINATE5: List<Float>? = null

        @SerializedName("GEOHASH")
        @Expose
        var gEOHASH: String? = null

        @SerializedName("ID")
        @Expose
        var iD = 0

        @SerializedName("NAME")
        @Expose
        var nAME: String? = null
    }
}
