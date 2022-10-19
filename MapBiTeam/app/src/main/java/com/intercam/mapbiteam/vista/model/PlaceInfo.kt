package com.intercam.mapbiteam.vista.model

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class PlaceInfo(
    val name:String,
    val address: String,
    val  phoneNumber: String,
    val  id :String,
    val websiteUri: Uri,
    val  latlng: LatLng,
    val  rating: Double,
    val attributions: String)
