package com.example.nimmaguru

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()

    fun addTutor(tutor: Tutor) {
        db.collection("tutors")
            .add(tutor)
    }

    fun getTutors(onResult: (List<Tutor>) -> Unit) {
        db.collection("tutors")
            .get()
            .addOnSuccessListener { result ->
                val list = mutableListOf<Tutor>()
                for (doc in result) {
                    val tutor = doc.toObject(Tutor::class.java)
                    list.add(tutor)
                }
                onResult(list)
            }
    }
}