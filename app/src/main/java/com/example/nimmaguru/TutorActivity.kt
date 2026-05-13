package com.example.nimmaguru

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TutorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorRegistrationScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorRegistrationScreen() {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TutorType.STUDENT) }
    var subject by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var freeHours by remember { mutableStateOf("") }

    var typeExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val welcomeMsg = "Tutor Registered Successfully!"
    val fillFieldsMsg = stringResource(R.string.err_required)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.guru_registration)) },
                navigationIcon = {
                    IconButton(onClick = { (context as? AppCompatActivity)?.finish() }) {
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
                .padding(20.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.register_choice_desc), 
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegistrationInputField(
                label = stringResource(R.string.full_name),
                value = name,
                onValueChange = { name = it }
            )

            // Tutor Type Dropdown
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = stringResource(R.string.register_as), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Box {
                    OutlinedTextField(
                        value = if (selectedType == TutorType.STUDENT) "Student" else "Professional",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().clickable { typeExpanded = true },
                        trailingIcon = {
                            IconButton(onClick = { typeExpanded = !typeExpanded }) {
                                Icon(Icons.Default.ArrowDropDown, null)
                            }
                        },
                        shape = MaterialTheme.shapes.medium
                    )
                    DropdownMenu(expanded = typeExpanded, onDismissRequest = { typeExpanded = false }) {
                        TutorType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name) },
                                onClick = {
                                    selectedType = type
                                    typeExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            RegistrationInputField(
                label = stringResource(R.string.subject),
                value = subject,
                onValueChange = { subject = it }
            )
            RegistrationInputField(
                label = "Age",
                value = age,
                onValueChange = { age = it }
            )
            RegistrationInputField(
                label = stringResource(R.string.experience),
                value = experience,
                onValueChange = { experience = it }
            )
            RegistrationInputField(
                label = stringResource(R.string.phone),
                value = phone,
                onValueChange = { phone = it }
            )
            RegistrationInputField(
                label = stringResource(R.string.availability),
                value = freeHours,
                onValueChange = { freeHours = it }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (name.isNotEmpty() && subject.isNotEmpty() && phone.isNotEmpty()) {
                        val tutor = Tutor(
                            name = name,
                            subject = subject,
                            age = age,
                            experience = experience
                        )
                        FirebaseHelper.addTutor(tutor)
                        Toast.makeText(context, welcomeMsg, Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, TutorDashboardActivity::class.java)
                        intent.putExtra("tutorName", name)
                        context.startActivity(intent)
                        (context as? AppCompatActivity)?.finish()
                    } else {
                        Toast.makeText(context, fillFieldsMsg, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Register & Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
