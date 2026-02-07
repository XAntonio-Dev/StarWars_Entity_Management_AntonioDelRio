package com.example.starwarsentitymanagementsystem.ui.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.starwarsentitymanagementsystem.R
import com.example.starwarsentitymanagementsystem.data.model.Species
import com.example.starwarsentitymanagementsystem.data.model.SpeciesConstants
import com.example.starwarsentitymanagementsystem.ui.components.AlertDialogOkCancel
import com.example.starwarsentitymanagementsystem.ui.components.BaseTopAppBarState
import com.example.starwarsentitymanagementsystem.ui.components.DropdownSelector
import com.example.starwarsentitymanagementsystem.ui.components.HeaderBox
import com.example.starwarsentitymanagementsystem.ui.theme.StarWarsEntityManagementSystemTheme

@Composable
fun EditSpeciesScreen(
    species: Species,
    onBack: () -> Unit,
    onShowSnackbar: (String) -> Unit,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit = {}
) {
    val viewModel = hiltViewModel<EditSpeciesViewModel>()
    val state = viewModel.state

    // Cargamos los datos de la especie al entrar
    LaunchedEffect(species) {
        viewModel.setSpecies(species)
        onConfigureTopBar(BaseTopAppBarState(title = "Editar: ${species.name}"))
    }

    // Si todo ha ido bien, volvemos atrás
    LaunchedEffect(state.isOperationSuccess) {
        if (state.isOperationSuccess) {
            onShowSnackbar("Operación realizada con éxito")
            onBack()
        }
    }

    // Agrupamos los eventos en la data class
    val events = EditSpeciesEvents(
        onNameChange = viewModel::onNameChange,
        onClassificationChange = viewModel::onClassificationChange,
        onDesignationChange = viewModel::onDesignationChange,
        onLanguageChange = viewModel::onLanguageChange,
        onDiscoveryDateChange = viewModel::onDiscoveryDateChange,
        onIsArtificialChange = viewModel::onIsArtificialChange,
        onUpdate = viewModel::onUpdateSpecies,
        onDelete = viewModel::showDeleteConfirmation,
        onCancel = onBack
    )

    EditSpeciesContent(
        state = state,
        events = events
    )

    // El diálogo de borrar
    if (state.showDeleteDialog) {
        AlertDialogOkCancel(
            title = "Confirmar eliminación",
            text = "¿Estás seguro de que deseas eliminar a ${state.name}?",
            okText = "Eliminar",
            cancelText = "Cancelar",
            onConfirm = { viewModel.onDeleteSpecies() },
            onDismiss = { viewModel.dismissDeleteDialog() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSpeciesContent(
    state: EditSpeciesState,
    events: EditSpeciesEvents
) {
    val classifications = SpeciesConstants.CLASSIFICATIONS

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderBox(
            imageRes = R.drawable.starwars_header,
            title = "Editar Especie"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre
            OutlinedTextField(
                value = state.name,
                onValueChange = events.onNameChange,
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth(),
                isError = state.nameError != null,
                supportingText = { state.nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // -- DROPDOWN MENU PARA CLASIFICACIÓN --
            DropdownSelector(
                label = stringResource(R.string.classification),
                options = SpeciesConstants.CLASSIFICATIONS,
                selectedOption = state.classification,
                onOptionSelected = events.onClassificationChange,
                isError = state.classificationError != null,
                errorMessage = state.classificationError
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Designación
            OutlinedTextField(
                value = state.designation,
                onValueChange = events.onDesignationChange,
                label = { Text(stringResource(R.string.designation)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Idioma
            OutlinedTextField(
                value = state.language,
                onValueChange = events.onLanguageChange,
                label = { Text(stringResource(R.string.language)) },
                modifier = Modifier.fillMaxWidth(),
                isError = state.languageError != null,
                supportingText = { state.languageError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Fecha
            OutlinedTextField(
                value = state.discoveryDate,
                onValueChange = events.onDiscoveryDateChange,
                label = { Text("Fecha (DD/MM/AAAA)") },
                placeholder = { Text("25/05/1977") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.discoveryDateError != null,
                supportingText = {
                    state.discoveryDateError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Checkbox(
                    checked = state.isArtificial,
                    onCheckedChange = events.onIsArtificialChange
                )
                Text(stringResource(R.string.artificial))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Guardar
            Button(
                onClick = events.onUpdate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSaving
            ) {
                Text(stringResource(R.string.save))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones secundarios (Cancelar y Borrar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = events.onCancel,
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.cancel))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = events.onDelete,
                    enabled = !state.isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete")
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(name = "1. Edición Estándar", showSystemUi = true)
@Preview(
    name = "2. Accesibilidad (Texto 1.5x)",
    showSystemUi = true,
    fontScale = 1.5f // Probamos si cabe el formulario con letra gigante
)
@Composable
fun PreviewEditSpeciesAdvanced() {
    StarWarsEntityManagementSystemTheme {
        EditSpeciesContent(
            state = EditSpeciesState(
                name = "Wookiee",
                classification = "Mammal",
                language = "Shyriiwook",
                discoveryDate = "1977",
                isArtificial = false
            ),
            events = EditSpeciesEvents() // Eventos vacíos
        )
    }
}

@Preview(name = "3. Estado de Error Visual", showBackground = true)
@Composable
fun PreviewEditSpeciesErrorState() {
    StarWarsEntityManagementSystemTheme {
        EditSpeciesContent(
            state = EditSpeciesState(
                name = "A", // Nombre muy corto
                nameError = "Mínimo 3 caracteres", // Simulamos el error
                classification = "",
                classificationError = "Campo requerido"
            ),
            events = EditSpeciesEvents()
        )
    }
}