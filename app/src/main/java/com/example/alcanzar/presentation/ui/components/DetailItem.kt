package com.example.alcanzar.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1976D2),
            modifier = Modifier.size(24.dp)
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Column {

            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}