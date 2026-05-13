package com.example.nimmaguru

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class TutorDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tutorId = intent.getStringExtra("tutorId") ?: ""
        val tutor = DataStore.tutorList.find { it.id == tutorId }

        if (tutor == null) {
            Toast.makeText(this, R.string.err_guru_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            TutorDetailScreen(tutorId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorDetailScreen(tutorId: String) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    
    val tutor by remember(tutorId) { 
        derivedStateOf { DataStore.tutorList.find { it.id == tutorId } ?: TutorModel(name = "Unknown") }
    }
    
    var thankYouNote by remember { mutableStateOf("") }
    var userRating by remember { mutableIntStateOf(0) }
    var showAiTip by remember { mutableStateOf(false) }
    var aiLoading by remember { mutableStateOf(false) }
    var aiInsight by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.guru_profile)) },
                navigationIcon = {
                    IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(context, TutorEditActivity::class.java)
                        intent.putExtra("tutorId", tutorId)
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(tutor.name.take(1), fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(tutor.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        if (tutor.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
                        }
                    }
                    Text(text = "${tutor.category} Expert", color = MaterialTheme.colorScheme.secondary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(18.dp))
                        Text(
                            text = String.format(Locale.getDefault(), " %.1f (%d ratings)", tutor.rating, tutor.ratingCount),
                            fontSize = 14.sp, 
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // GenAI Integration Button (Mock AI)
            Button(
                onClick = {
                    aiLoading = true
                    scope.launch {
                        delay(1200)
                        aiInsight = "AI Analysis: ${tutor.name} has a high 'Patience Score'. Recommended for students in ${tutor.village} who prefer evening sessions."
                        aiLoading = false
                        showAiTip = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                if (aiLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(stringResource(R.string.ai_loading))
                } else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.ai_guru_tips))
                }
            }

            if (showAiTip) {
                Card(
                    modifier = Modifier.padding(top = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.ai_tip_title), fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text(aiInsight, fontSize = 15.sp)
                        TextButton(onClick = { showAiTip = false }, modifier = Modifier.align(Alignment.End)) {
                            Text(stringResource(R.string.close))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Rating Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(R.string.rate_guru), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= userRating) Icons.Default.Star else Icons.Outlined.StarBorder,
                                contentDescription = "Rate $i stars",
                                tint = if (i <= userRating) Color(0xFFFFB300) else Color.Gray,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable { userRating = i }
                            )
                        }
                    }
                    if (userRating > 0) {
                        Button(
                            onClick = {
                                val currentCount = tutor.ratingCount
                                val currentRating = tutor.rating
                                val newRating = ((currentRating * currentCount) + userRating) / (currentCount + 1)
                                
                                val updatedTutor = tutor.copy(
                                    rating = newRating,
                                    ratingCount = currentCount + 1
                                )
                                DataStore.updateTutor(updatedTutor)
                                userRating = 0
                                Toast.makeText(context, R.string.rating_submitted, Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.align(Alignment.End).height(56.dp)
                        ) {
                            Text(stringResource(R.string.submit_rating))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Details
            Text(stringResource(R.string.guru_profile), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(stringResource(R.string.qualification), tutor.education)
                    if (tutor.college.isNotEmpty()) DetailRow(stringResource(R.string.college_org), tutor.college)
                    DetailRow(stringResource(R.string.experience), "${tutor.experience} Years")
                    DetailRow(stringResource(R.string.village), tutor.village)
                    DetailRow(stringResource(R.string.availability), tutor.freeHours.ifEmpty { "On Request" })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:${tutor.phone}") }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f).height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.call_now), fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("sms:${tutor.phone}")
                            putExtra("sms_body", "Hi ${tutor.name}, I am interested in your classes for ${tutor.subject}.")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f).height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.message), fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Notes
            Text(text = stringResource(R.string.thank_you_notes), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = thankYouNote,
                onValueChange = { thankYouNote = it },
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
                placeholder = { Text(stringResource(R.string.write_note)) },
                trailingIcon = {
                    IconButton(onClick = {
                        if (thankYouNote.isNotEmpty()) {
                            val updatedNotes = tutor.thankYouNotes.toMutableList()
                            updatedNotes.add(0, thankYouNote)
                            DataStore.updateTutor(tutor.copy(thankYouNotes = updatedNotes))
                            thankYouNote = ""
                        }
                    }) {
                        Icon(Icons.Default.Send, contentDescription = "Send Note")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            tutor.thankYouNotes.forEach { note ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = note, fontSize = 14.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
