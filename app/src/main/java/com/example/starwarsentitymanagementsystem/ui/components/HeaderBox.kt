package com.example.starwarsentitymanagementsystem.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwarsentitymanagementsystem.ui.theme.starWarsYellow

@Composable
fun HeaderBox(imageRes: Int, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(244.dp)
    ) {
        // Imagen y overlay
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .background(Color(0xAA000000))
                .matchParentSize()
        )

        // Column para manejar el espacio
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp), // separacion inferior
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(166.dp)) // este es el “margen superior extra”
            Text(
                text = title,
                color = starWarsYellow,
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
