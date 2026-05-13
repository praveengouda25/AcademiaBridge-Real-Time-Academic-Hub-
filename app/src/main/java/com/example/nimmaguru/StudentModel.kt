package com.example.nimmaguru

import java.util.UUID

data class StudentModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val grade: String,
    val subjectsNeeded: List<String>,
    val village: String,
    val contactNumber: String,
    val additionalNote: String = ""
)