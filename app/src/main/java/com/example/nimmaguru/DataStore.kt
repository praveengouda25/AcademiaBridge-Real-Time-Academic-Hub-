package com.example.nimmaguru

import androidx.compose.runtime.mutableStateListOf

object DataStore {
    val tutorList = mutableStateListOf<TutorModel>(
        TutorModel(
            name = "Ramesh Rao",
            category = Category.SCHOOL,
            tutorType = TutorType.WORKING_PROFESSIONAL,
            subject = "Mathematics",
            education = "M.Sc Mathematics",
            jobTitle = "Retired HOD Math",
            experience = "35",
            village = "Kengeri",
            phone = "9845012345",
            rating = 4.9f,
            ratingCount = 120,
            freeHours = "Sat 4:00 PM - 6:00 PM",
            thankYouNotes = listOf("Excellent teaching style!", "Very patient with kids")
        ),
        TutorModel(
            name = "Sagar M",
            category = Category.ENGINEERING,
            tutorType = TutorType.STUDENT,
            subject = "Data Structures",
            education = "BE 3rd Year (CSE)",
            college = "RVCE",
            experience = "2",
            village = "Malleswaram",
            phone = "9900112233",
            rating = 4.8f,
            ratingCount = 45,
            freeHours = "Sun 10:00 AM - 12:00 PM"
        ),
        TutorModel(
            name = "Dr. Sandeep Hegde",
            category = Category.MEDICAL,
            tutorType = TutorType.WORKING_PROFESSIONAL,
            subject = "Anatomy",
            education = "MBBS, MD",
            jobTitle = "Associate Professor",
            experience = "15",
            village = "Jayanagar",
            phone = "9880011223",
            rating = 5.0f,
            ratingCount = 89,
            freeHours = "Mon 6:00 PM - 8:00 PM"
        ),
        TutorModel(
            name = "Latha Mani",
            category = Category.SCHOOL,
            tutorType = TutorType.WORKING_PROFESSIONAL,
            subject = "Kannada",
            education = "B.A. Kannada",
            jobTitle = "Primary School Teacher",
            experience = "20",
            village = "Jayanagar",
            phone = "9448001122",
            rating = 4.9f,
            ratingCount = 67,
            freeHours = "Sun 2:00 PM - 4:00 PM",
            thankYouNotes = listOf("Kannada grammar is now easy for me.")
        )
    )

    val studentList = mutableStateListOf<StudentModel>(
        StudentModel(name = "Aditi R", grade = "1st Std", subjectsNeeded = listOf("Maths", "EVS"), village = "Rajajinagar", contactNumber = "9000000001"),
        StudentModel(name = "Arjun K", grade = "10th Std", subjectsNeeded = listOf("Maths"), village = "Kengeri", contactNumber = "9000000010"),
        StudentModel(name = "Megha S", grade = "MBBS 1st Year", subjectsNeeded = listOf("Anatomy"), village = "Jayanagar", contactNumber = "9000000014")
    )

    fun updateTutor(updatedTutor: TutorModel) {
        val index = tutorList.indexOfFirst { it.id == updatedTutor.id }
        if (index != -1) {
            tutorList[index] = updatedTutor
        }
    }
}