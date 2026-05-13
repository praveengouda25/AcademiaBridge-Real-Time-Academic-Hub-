package com.example.nimmaguru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScaffold(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavHostController) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = when (currentDestination?.route) {
                            "home" -> stringResource(R.string.nav_home)
                            "register" -> stringResource(R.string.nav_register)
                            "schedule" -> stringResource(R.string.nav_schedule)
                            "wall_of_fame" -> stringResource(R.string.nav_wall_of_fame)
                            else -> "Nimma Guru"
                        },
                        fontWeight = FontWeight.Bold 
                    ) 
                },
                actions = {
                    // Language Toggle logic
                    val currentLocales = AppCompatDelegate.getApplicationLocales()
                    val isKannada = !currentLocales.isEmpty && currentLocales.get(0)?.language == "kn"
                    
                    TextButton(onClick = {
                        val appLocale: LocaleListCompat = if (isKannada) {
                            LocaleListCompat.forLanguageTags("en")
                        } else {
                            LocaleListCompat.forLanguageTags("kn")
                        }
                        AppCompatDelegate.setApplicationLocales(appLocale)
                    }) {
                        Text(
                            text = if (isKannada) "English" else "ಕನ್ನಡ", 
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Logout Button
                    IconButton(onClick = {
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = stringResource(R.string.logout))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Triple("home", stringResource(R.string.nav_home), Icons.Default.Home),
                    Triple("register", stringResource(R.string.nav_register), Icons.Default.AccountCircle),
                    Triple("schedule", stringResource(R.string.nav_schedule), Icons.Default.DateRange),
                    Triple("wall_of_fame", stringResource(R.string.nav_wall_of_fame), Icons.Default.Star)
                )
                
                items.forEach { (route, label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(label) },
                        selected = currentDestination?.route == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { MainLandingScreen(navController) }
            composable("register") { RegistrationChoiceScreen() }
            composable("schedule") { ClassCalendarScreen() }
            composable("wall_of_fame") { WallOfFameScreen() }
        }
    }
}

@Composable
fun MainLandingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D47A1), Color(0xFF1976D2))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nimma Guru",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.professional_tuitions),
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            RoleCard(
                title = stringResource(R.string.register_as_tutor),
                description = stringResource(R.string.tutor_desc),
                icon = Icons.Default.Face,
                color = Color(0xFF4CAF50),
                onClick = {
                    val intent = Intent(context, TutorActivity::class.java)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                title = stringResource(R.string.register_as_student),
                description = stringResource(R.string.student_desc),
                icon = Icons.Default.AccountCircle,
                color = Color(0xFFE91E63),
                onClick = {
                    val intent = Intent(context, StudentRegistrationActivity::class.java)
                    context.startActivity(intent)
                }
            )
            
            Spacer(modifier = Modifier.height(30.dp))
            
            HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = stringResource(R.string.quick_access),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        val intent = Intent(context, StudentActivity::class.java)
                        intent.putExtra("name", "Demo Student")
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder(true)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.view_gurus), fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = {
                        val intent = Intent(context, TutorDashboardActivity::class.java)
                        intent.putExtra("tutorName", "Demo Guru")
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder(true)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.view_leads), fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun RegistrationChoiceScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.nav_register), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { context.startActivity(Intent(context, TutorActivity::class.java)) }, 
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Text(stringResource(R.string.register_as_tutor), fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { context.startActivity(Intent(context, StudentRegistrationActivity::class.java)) }, 
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Text(stringResource(R.string.register_as_student), fontSize = 18.sp)
        }
    }
}

@Composable
fun ClassCalendarScreen() {
    val gurusWithFreeHours = DataStore.tutorList.filter { it.freeHours.isNotEmpty() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = stringResource(R.string.class_calendar), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.samudaya_bhavana), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (gurusWithFreeHours.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No upcoming sessions scheduled.", color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(gurusWithFreeHours) { guru ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "${guru.subject} with ${guru.name}", fontWeight = FontWeight.Bold)
                                Text(text = guru.freeHours, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WallOfFameScreen() {
    val topTutors = DataStore.tutorList.sortedWith(
        compareByDescending<TutorModel> { it.rating }.thenByDescending { it.thankYouNotes.size }
    )
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = stringResource(R.string.nav_wall_of_fame), style = MaterialTheme.typography.headlineMedium)
        Text(text = stringResource(R.string.top_gurus_desc), fontSize = 14.sp, color = Color.Gray)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(topTutors) { tutor ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val intent = Intent(context, TutorDetailActivity::class.java)
                        intent.putExtra("tutorId", tutor.id)
                        context.startActivity(intent)
                    }
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(60.dp), 
                            shape = CircleShape, 
                            color = MaterialTheme.colorScheme.primaryContainer,
                            tonalElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(tutor.name.take(1), fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(tutor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(tutor.subject, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
                            if (tutor.thankYouNotes.isNotEmpty()) {
                                Text(
                                    text = "${tutor.thankYouNotes.size} Appreciation Notes", 
                                    fontSize = 12.sp, 
                                    color = Color(0xFF43A047),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(24.dp))
                            Text(text = String.format(Locale.getDefault(), "%.1f", tutor.rating), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoleCard(title: String, description: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = color.copy(alpha = 0.15f),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.padding(14.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
