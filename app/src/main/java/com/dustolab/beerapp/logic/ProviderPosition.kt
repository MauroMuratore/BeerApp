package com.dustolab.beerapp.logic

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class ProviderPosition(
    var contenxt: Context
) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            contenxt,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    contenxt,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPosition(): Boolean{
        val locationManager: LocationManager = contenxt.getSystemService(Context.LOCATION_SERVICE)
                as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun getPosition(): Task<Location>{
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(contenxt)
        return fusedLocationProviderClient.lastLocation

    }


}