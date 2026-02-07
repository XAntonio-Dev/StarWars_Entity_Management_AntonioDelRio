package com.example.starwarsentitymanagementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.example.starwarsentitymanagementsystem.home.NavHostScreen
import com.example.starwarsentitymanagementsystem.ui.base.common.LocalSpacing
import com.example.starwarsentitymanagementsystem.ui.base.common.SpacingDataClass
import com.example.starwarsentitymanagementsystem.ui.base.common.SpacingStyle
import com.example.starwarsentitymanagementsystem.ui.theme.StarWarsEntityManagementSystemTheme
import com.example.starwarsentitymanagementsystem.ui.theme.StarWarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myAppSpacing = SpacingDataClass(
            SpacingStyle.small,
            SpacingStyle.medium,
            SpacingStyle.large
        )

        setContent {
            StarWarsTheme {
                StarWarsEntityManagementSystemTheme {
                    CompositionLocalProvider(LocalSpacing provides myAppSpacing) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NavHostScreen()
                        }
                    }
                }
            }
        }
    }
}