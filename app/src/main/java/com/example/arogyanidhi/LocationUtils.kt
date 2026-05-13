//package com.example.arogyanidhi

package com.example.arogyanidhi.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object LocationUtils {
    fun searchHospitalsNearby(context: Context) {
        // geo:0,0?q=hospitals searches for 'hospitals' near current location
        val gmmIntentUri = Uri.parse("geo:0,0?q=hospitals")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        // This opens the external Google Maps app
        context.startActivity(mapIntent)
    }
}