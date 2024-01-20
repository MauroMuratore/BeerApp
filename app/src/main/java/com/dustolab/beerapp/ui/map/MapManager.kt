package com.dustolab.beerapp.ui.map

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.dustolab.beerapp.R
import com.dustolab.beerapp.model.Address
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapManager(
    private var mapView: MapView,
    private var context: Context
) {

    fun showMap(): MapboxMap {
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        return mapView.getMapboxMap()
    }

    fun setAnnotation(longitude: Double, latitude: Double): PointAnnotationManager? {
        val bitmap = bitmapFromDrawableRes(
            context,
            R.drawable.red_marker
        )
        val annotationApi = mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()

        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(longitude,latitude))
            .withIconImage(bitmap)
            .withIconAnchor(IconAnchor.BOTTOM)
        pointAnnotationManager?.create(pointAnnotationOptions)
        return pointAnnotationManager

    }

    fun setUserAnnotation(longitude: Double, latitude: Double){
        val annotationApi = mapView.annotations
        val circleAnnotationManager = annotationApi.createCircleAnnotationManager()

        val circleAnnotationOptions = CircleAnnotationOptions()
            .withPoint(Point.fromLngLat(longitude, latitude))
            // Style the circle that will be added to the map.
            .withCircleRadius(8.0)
            .withCircleColor("#ee4e8b")
            .withCircleStrokeWidth(2.0)
            .withCircleStrokeColor("#ffffff")
        circleAnnotationManager.create(circleAnnotationOptions)
    }

    fun setLocation(longitude: Double, latitude: Double) {
        var cameraPosition = CameraOptions.Builder().center(
            Point.fromLngLat(longitude, latitude)
        )
            .zoom(13.0)
            .build()

        mapView.getMapboxMap().flyTo(cameraPosition)


    }


    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId)!!)

    private fun convertDrawableToBitmap(sourceDrawable: Drawable): Bitmap{
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState
            val drawable = constantState!!.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}
