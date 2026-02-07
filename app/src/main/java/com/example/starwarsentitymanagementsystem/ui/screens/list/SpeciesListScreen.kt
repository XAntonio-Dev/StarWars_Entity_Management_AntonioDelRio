package com.example.starwarsentitymanagementsystem.ui.screens.list

import SpeciesItem
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.starwarsentitymanagementsystem.R
import com.example.starwarsentitymanagementsystem.data.model.Species
import com.example.starwarsentitymanagementsystem.ui.base.common.LocalSpacing
import com.example.starwarsentitymanagementsystem.ui.components.Action
import com.example.starwarsentitymanagementsystem.ui.components.AlertDialogOkCancel
import com.example.starwarsentitymanagementsystem.ui.components.BaseTopAppBarState
import com.example.starwarsentitymanagementsystem.ui.components.HeaderBox
import com.example.starwarsentitymanagementsystem.ui.screens.LoadingScreen
import com.example.starwarsentitymanagementsystem.ui.screens.NoDataScreen
import com.example.starwarsentitymanagementsystem.ui.theme.StarWarsEntityManagementSystemTheme

@Composable
fun SpeciesListScreen(
    modifier: Modifier = Modifier,
    speciesList: SpeciesListState,
    speciesToDelete: Species?,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onEdit: (Species) -> Unit,
    onRequestDelete: (Species) -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDelete: () -> Unit,
    onSort: () -> Unit,
    onAbout: () -> Unit
) {
    // Configuración TopBar: Solo el menú de "About Us"
    LaunchedEffect(Unit) {
        onConfigureTopBar(BaseTopAppBarState(
            title = "Listado de Especies",
            actions = listOf(
                Action.MenuItem(
                    title = "Sobre Nosotros",
                    onClick = onAbout
                )
            )
        ))
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (speciesList) {
            is SpeciesListState.Loading -> LoadingScreen()
            is SpeciesListState.NoData -> NoDataScreen()
            is SpeciesListState.Success -> {
                SpeciesListContent(
                    list = speciesList.dataset,
                    onEdit = onEdit,
                    onSort = onSort,
                    onRequestDelete = onRequestDelete
                )
            }
        }
    }

    if (speciesToDelete != null) {
        AlertDialogOkCancel(
            title = "Eliminar Especie",
            text = "¿Estás seguro de que deseas eliminar a ${speciesToDelete.name}?",
            okText = "Eliminar",
            cancelText = "Cancelar",
            onConfirm = onConfirmDelete,
            onDismiss = onDismissDelete
        )
    }
}

@Composable
fun SpeciesListContent(
    list: List<Species>,
    onEdit: (Species) -> Unit,
    onSort: () -> Unit,
    onRequestDelete: (Species) -> Unit
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        item {
            // Header con el botón Sort integrado
            Box(modifier = Modifier.fillMaxWidth()) {
                HeaderBox(imageRes = R.drawable.starwars_header, title = "List Species")

                IconButton(
                    onClick = onSort,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Ordenar",
                        tint = Color.White
                    )
                }
            }
        }

        items(list) { species ->
            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                SpeciesItem(
                    species = species,
                    onEdit = { onEdit(species) },
                    onLongClick = { onRequestDelete(species) }
                )
            }
        }
    }
}


@Preview(name = "1. Modo Claro (Pixel 4)", group = "Listado", showSystemUi = true, device = Devices.PIXEL_4)
@Preview(
    name = "2. Modo Oscuro",
    group = "Listado",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "3. Tablet (Landscape)",
    group = "Listado",
    showSystemUi = true,
    device = Devices.PIXEL_C
)
@Composable
fun PreviewSpeciesListAdvanced() {
    StarWarsEntityManagementSystemTheme {
        val dataEjemplo = listOf(
            Species(1, "Wookiee", "Mammal", "Sentient", "Shyriiwook", "Hairy", "1977", false),
            Species(2, "Droid", "Artificial", "Sentient", "Binary", "Metal", "1977", true),
            Species(3, "Hutt", "Gastropod", "Sentient", "Huttese", "Slug", "1983", false),
            Species(4, "Ewok", "Mammal", "Sentient", "Ewokese", "Small", "1983", false),
            Species(5, "Yoda's Species", "Unknown", "Sentient", "Galactic", "Green", "1980", false)
        )

        SpeciesListContent(
            list = dataEjemplo,
            onEdit = {},
            onSort = {},
            onRequestDelete = {}
        )
    }
}