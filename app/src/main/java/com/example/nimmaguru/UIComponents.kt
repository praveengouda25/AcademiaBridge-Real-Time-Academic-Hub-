package com.example.nimmaguru

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegistrationInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isLarge: Boolean = false
) {
    Column(modifier = Modifier.padding(vertical = if (isLarge) 16.dp else 12.dp)) {
        Text(
            text = label,
            fontSize = if (isLarge) 22.sp else 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp),
            textStyle = TextStyle(fontSize = if (isLarge) 24.sp else 18.sp),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.width(130.dp),
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun getCategoryDisplayName(category: Category): String {
    return when (category) {
        Category.SCHOOL -> stringResource(R.string.cat_school)
        Category.ENGINEERING -> stringResource(R.string.cat_engineering)
        Category.MEDICAL -> stringResource(R.string.cat_medical)
        Category.COMMERCE -> stringResource(R.string.cat_commerce)
        Category.SKILLS -> stringResource(R.string.cat_skills)
        Category.OTHER -> stringResource(R.string.cat_other)
    }
}
