# AcademiaBridge – Real-Time Academic Hub 

## Project Overview

AcademiaBridge – Real-Time Academic Hub  is an Android-based healthcare support application developed to improve emergency blood donation management and communication between blood donors, patients, and hospitals. The application provides a real-time digital platform that helps users quickly find compatible blood donors during medical emergencies.

Traditionally, finding blood donors during emergencies depends heavily on phone calls, social media posts, and personal contacts, which often causes delays and confusion. NimmaGuru Connect addresses this problem by offering a structured, fast, and cloud-connected solution that simplifies donor registration, emergency blood requests, and donor matching.

The application was developed as part of an internship project under **MindMatrix**, focusing on real-world healthcare problem-solving using Android development, Firebase cloud services, and modern mobile technologies.

---

# Objectives of the Project

- To digitalize emergency blood donor management
- To reduce delays in finding blood donors during emergencies
- To provide real-time communication between donors and patients
- To maintain a centralized donor database
- To improve healthcare accessibility using mobile technology
- To encourage voluntary blood donation through a user-friendly platform

---

# Key Features

## Donor Registration System
Allows users to register as blood donors by entering details such as:
- Name
- Blood Group
- Contact Number
- Availability Status

The donor information is securely stored in Firebase Firestore.

---

## Emergency Blood Request System
Hospitals or users can create emergency blood requests by providing:
- Patient Details
- Required Blood Group
- Hospital Information
- Emergency Notes

This helps in quickly processing emergency blood requirements.

---

## Real-Time Donor Alerts
The application supports real-time notifications and synchronization using Firebase, enabling nearby donors to receive emergency requests instantly.

---

## Blood Group-Based Matching
The system filters and identifies donors based on compatible blood groups, ensuring efficient donor matching during emergencies.

---

## Donor Availability Management
Donors can update their availability status after donating blood or during personal situations. This ensures that only available donors are considered for emergency requests.

---

## Secure Donor Privacy
Donor information is protected and shared only after request acceptance, improving privacy and user trust.

---

## User-Friendly Interface
The application is designed with a clean and simple user interface using modern Android development practices for better accessibility and usability.

---

## Firebase Cloud Integration
The project uses Firebase Firestore for:
- Real-time database synchronization
- Secure cloud storage
- Efficient request management
- Instant data updates across devices

---

# Functional Workflow

1. Users register themselves as blood donors.
2. Donor information is stored securely in Firebase.
3. Hospitals or users create emergency blood requests.
4. The system identifies compatible donors.
5. Nearby and available donors receive emergency alerts.
6. Donors can accept or decline requests.
7. Upon acceptance, secure communication is enabled between both parties.
8. Donor availability updates are synchronized in real-time.

---

# Technologies Used

## Frontend
- Android XML
- Jetpack Compose
- Material Design Components

## Programming Languages
- Kotlin
- Java

## Backend & Cloud
- Firebase Firestore
- Firebase Authentication
- Google Services

## Development Tools
- Android Studio
- Gradle
- Git & GitHub

## Database
- Firebase Cloud Firestore

---

# Software Requirements

- Android Studio Hedgehog or above
- JDK 17
- Android SDK 35+
- Firebase Project Setup
- Internet Connection

---

# Hardware Requirements

- Minimum 8GB RAM
- Intel i5 Processor or above
- Android Device or Emulator
- Minimum 10GB Free Storage

---

# Firebase Configuration

The project uses Firebase services for cloud database integration.

To run the project successfully:

1. Create a Firebase project
2. Register the Android application
3. Download the `google-services.json` file
4. Place it inside the project root/app module
5. Sync Gradle files

---

# Project Structure

```bash
NimmaGuru/
│
├── app/
├── manifests/
├── java/
├── res/
├── Gradle Scripts/
├── google-services.json
└── README.md
