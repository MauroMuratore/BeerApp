package com.dustolab.beerapp.logic.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImageRepository(
    private val dbReference: StorageReference = Firebase.storage.reference
){

    fun loadImage(uid: String): Task<ByteArray> {
        val imageRef = dbReference.child(uid+".jpg")
        val ONE_MEGABYTE: Long = 1024 * 1024
        val imageByte = imageRef.getBytes(ONE_MEGABYTE)
        return imageByte
    }

}