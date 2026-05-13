package com.example.nimmaguru

import java.util.UUID

enum class Category {
    SCHOOL, ENGINEERING, MEDICAL, COMMERCE, SKILLS, OTHER
}

enum class TutorType {
    STUDENT, WORKING_PROFESSIONAL
}

data class TutorModel(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var category: Category = Category.SCHOOL,
    var tutorType: TutorType = TutorType.STUDENT,
    var subject: String = "",
    var skills: List<String> = emptyList(),
    var age: String = "",
    var education: String = "",
    var college: String = "",
    var jobTitle: String = "",
    var experience: String = "",
    var freeHours: String = "",
    var village: String = "",
    var phone: String = "9876543210",
    var rating: Float = 5.0f,
    var ratingCount: Int = 1,
    var isVerified: Boolean = true,
    var thankYouNotes: List<String> = emptyList()
)
