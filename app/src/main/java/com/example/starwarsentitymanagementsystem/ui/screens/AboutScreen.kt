package com.example.starwarsentitymanagementsystem.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.starwarsentitymanagementsystem.R
import com.example.starwarsentitymanagementsystem.ui.components.HeaderBox
import com.example.starwarsentitymanagementsystem.ui.theme.StarWarsEntityManagementSystemTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    AboutContent(modifier = modifier)
}

@Composable
fun AboutContent(modifier: Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderBox(
            imageRes = R.drawable.starwars_header,
            title = "about us"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "informacion del desarrollador",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Detalles del desarrollador
                Text(text = "Desarrollador: Antonio Del Río", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Curso: 2º DAM", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Fecha: 22/11/2025", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAboutScreen() {
    StarWarsEntityManagementSystemTheme {
        AboutScreen()
    }
}