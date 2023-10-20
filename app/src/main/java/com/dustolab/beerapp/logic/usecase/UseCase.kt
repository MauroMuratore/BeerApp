package com.dustolab.beerapp.logic.usecase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface UseCase {
    fun useCase(): Task<QuerySnapshot>
}