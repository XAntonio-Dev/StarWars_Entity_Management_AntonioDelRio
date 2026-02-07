package com.example.starwarsentitymanagementsystem.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

// Definimos si la acción es un botón directo o va al menú desplegable
sealed class Action(open val onClick: () -> Unit) {
    data class IconButton(
        val icon: ImageVector,
        val description: String,
        override val onClick: () -> Unit
    ) : Action(onClick)

    data class MenuItem(
        val title: String,
        override val onClick: () -> Unit
    ) : Action(onClick)
}

// Estado para controlar título, iconos y menú
data class BaseTopAppBarState(
    val title: String = "",
    val navigationIcon: ImageVector? = null,
    val onNavigationClick: () -> Unit = {},
    val actions: List<Action> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(state: BaseTopAppBarState) {
    // Separamos las acciones visibles de las del menú "More"
    val visibleActions = state.actions.filterIsInstance<Action.IconButton>()
    val overflowActions = state.actions.filterIsInstance<Action.MenuItem>()

    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(state.title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        navigationIcon = {
            if (state.navigationIcon != null) {
                IconButton(onClick = state.onNavigationClick) {
                    Icon(state.navigationIcon, contentDescription = "Navegación")
                }
            }
        },
        actions = {
            // 1. Iconos directos (si los hubiera)
            visibleActions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(action.icon, contentDescription = action.description)
                }
            }

            // 2. Menú de 3 puntos (About Us)
            if (overflowActions.isNotEmpty()) {
                Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        overflowActions.forEach { action ->
                            DropdownMenuItem(
                                text = { Text(action.title) },
                                onClick = {
                                    menuExpanded = false
                                    action.onClick()
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}