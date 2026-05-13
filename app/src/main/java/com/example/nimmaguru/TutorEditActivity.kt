package com.example.nimmaguru

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TutorEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tutorId = intent.getStringExtra("tutorId") ?: ""
        val tutor = DataStore.tutorList.find { it.id == tutorId }

        if (tutor == null) {
            finish()
            return
        }

        setContent {
            TutorEditScreen(tutor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorEditScreen(tutor: TutorModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(tutor.name) }
    var subject by remember { mutableStateOf(tutor.subject) }
    var experience by remember { mutableStateOf(tutor.experience) }
    var village by remember { mutableStateOf(tutor.village) }
    var freeHours by remember { mutableStateOf(tutor.freeHours) }
    
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_profile), fontSize = 24.sp) },
                navigationIcon = {
                    IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Update your details below. We use large text and buttons to make it easy for everyone to use.",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Using the standardized RegistrationInputField for elderly-friendly UX (minHeight 64dp, large text)
            RegistrationInputField(label = stringResource(R.string.full_name), value = name, onValueChange = { name = it }, isLarge = true)
            RegistrationInputField(label = stringResource(R.string.subject), value = subject, onValueChange = { subject = it }, isLarge = true)
            RegistrationInputField(label = stringResource(R.string.experience), value = experience, onValueChange = { experience = it }, isLarge = true)
            RegistrationInputField(label = stringResource(R.string.village), value = village, onValueChange = { village = it }, isLarge = true)
            RegistrationInputField(label = stringResource(R.string.availability), value = freeHours, onValueChange = { freeHours = it }, isLarge = true)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val updatedTutor = tutor.copy(
                        name = name,
                        subject = subject,
                        experience = experience,
                        village = village,
                        freeHours = freeHours
                    )
                    DataStore.updateTutor(updatedTutor)
                    Toast.makeText(context, context.getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
                    (context as? ComponentActivity)?.finish()
                },
                modifier = Modifier.fillMaxWidth().height(72.dp), // Extra large button for elderly users
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.save), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
