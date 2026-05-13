package com.example.nimmaguru

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class StudentRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentRegistrationScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationScreen() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var subjects by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    
    val errRequired = stringResource(R.string.err_required)
    val successMsg = "Welcome $name! Guru search is active."

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.student_registration)) },
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
            Text(text = stringResource(R.string.find_gurus_desc), fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            RegistrationInputField(label = stringResource(R.string.full_name), value = name, onValueChange = { name = it })
            RegistrationInputField(label = "Current Grade / Course", value = grade, onValueChange = { grade = it })
            RegistrationInputField(label = stringResource(R.string.subject) + " (comma separated)", value = subjects, onValueChange = { subjects = it })
            RegistrationInputField(label = stringResource(R.string.village), value = village, onValueChange = { village = it })
            RegistrationInputField(label = stringResource(R.string.phone), value = contact, onValueChange = { contact = it })
            RegistrationInputField(label = "Special Requirements", value = note, onValueChange = { note = it })

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (name.isNotEmpty() && subjects.isNotEmpty() && contact.isNotEmpty()) {
                        val subjectsList = subjects.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                        DataStore.studentList.add(
                            StudentModel(
                                name = name,
                                grade = grade,
                                subjectsNeeded = subjectsList,
                                village = village,
                                contactNumber = contact,
                                additionalNote = note
                            )
                        )
                        Toast.makeText(context, successMsg, Toast.LENGTH_LONG).show()
                        
                        val intent = Intent(context, StudentActivity::class.java)
                        intent.putExtra("name", name)
                        context.startActivity(intent)
                        (context as? AppCompatActivity)?.finish()
                    } else {
                        Toast.makeText(context, errRequired, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Register & Find Gurus", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
