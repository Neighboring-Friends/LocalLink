package com.example.nextdoorfriend.attraction

import com.google.android.gms.maps.model.LatLng

data class Attraction(
    var location: LatLng?,
    var address: String,
    var attractName: String,
    var attractContents: String,
    val tel: String,
    val homepage: String,
    val email: String,
    var attr01: String,
    var attr02: String,
    var attr03: String,
    var attr04: String,
    var attr05: String
) {
}