package com.example.starwarsentitymanagementsystem.ui.screens.add

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.starwarsentitymanagementsystem.R
import com.example.starwarsentitymanagementsystem.data.model.SpeciesConstants
import com.example.starwarsentitymanagementsystem.permissions.AppPermissions
import com.example.starwarsentitymanagementsystem.ui.components.AlertDialogOkCancel
import com.example.starwarsentitymanagementsystem.ui.components.DropdownSelector
import com.example.starwarsentitymanagementsystem.ui.components.HeaderBox
import com.example.starwarsentitymanagementsystem.ui.helper.NotificationHandler
import com.example.starwarsentitymanagementsystem.ui.helper.rememberPermissionsLauncher
import com.example.starwarsentitymanagementsystem.ui.theme.StarWarsEntityManagementSystemTheme

@Composable
fun AddSpeciesScreen(
    viewModel: AddSpeciesViewModel,
    onCreate: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state
    val context = LocalContext.current

    // Instancio el handler de notificaciones aquí
    val notificationHandler = remember { NotificationHandler(context) }

    // Launcher de permisos para Android 13+
    val requestNotificationPermissionThenFinalize = rememberPermissionsLauncher(
        permissions = listOf(AppPermissions.Notifications),
        onAllGranted = {
            // Si hay permiso, lanzamos la noti de éxito
            notificationHandler.showSimpleNotification(
                contentTitle = "Especie Creada",
                contentText = "Se ha dado de alta ${state.name}"
            )
            onCreate()
            viewModel.resetState()
        },
        onDenied = {
            // Si no, avisamos con un Toast sencillito
            Toast.makeText(context, "Guardado sin notificación", Toast.LENGTH_SHORT).show()
            onCreate()
            viewModel.resetState()
        }
    )

    // Lógica del botón Crear
    val handleCreateClick = {
        if (viewModel.validateForm()) {
            // Primero miramos si está repe en la BD
            viewModel.checkDuplicateAndSave(
                onSuccess = { requestNotificationPermissionThenFinalize() }
            )
        }
    }

    // El AlertDialog por si intentan colar un duplicado
    if (state.showDuplicateError) {
        AlertDialogOkCancel(
            title = "Error de Integridad",
            text = "La especie '${state.name}' ya existe. Prueba con otro nombre.",
            okText = "Entendido",
            cancelText = "",
            onConfirm = { viewModel.onDismissDialog() },
            onDismiss = { viewModel.onDismissDialog() }
        )
    }

    val events = AddSpeciesEvents(
        onNameChange = viewModel::onNameChange,
        onClassificationChange = viewModel::onClassificationChange,
        onDesignationChange = viewModel::onDesignationChange,
        onLanguageChange = viewModel::onLanguageChange,
        onDiscoveryDateChange = viewModel::onDiscoveryDateChange,
        onIsArtificialChange = viewModel::onIsArtificialChange,
        onCreate = handleCreateClick,
        onCancel = onCancel
    )

    AddSpeciesContent(
        modifier = modifier,
        state = state,
        events = events
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSpeciesContent(
    modifier: Modifier = Modifier,
    state: AddSpeciesState,
    events: AddSpeciesEvents
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
            title = stringResource(R.string.add_species)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear Nueva Especie",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -- NOMBRE --
            OutlinedTextField(
                value = state.name,
                onValueChange = events.onNameChange,
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth(),
                isError = state.nameError != null,
                supportingText = { state.nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // -- CLASIFICACIÓN (DROPDOWN MENU) --
            DropdownSelector(
                label = stringResource(R.string.classification),
                options = SpeciesConstants.CLASSIFICATIONS,
                selectedOption = state.classification,
                onOptionSelected = events.onClassificationChange,
                isError = state.classificationError != null,
                errorMessage = state.classificationError
            )

            Spacer(modifier = Modifier.height(8.dp))

            // -- OTROS CAMPOS --
            OutlinedTextField(
                value = state.designation,
                onValueChange = events.onDesignationChange,
                label = { Text(stringResource(R.string.designation)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.language,
                onValueChange = events.onLanguageChange,
                label = { Text(stringResource(R.string.language)) },
                modifier = Modifier.fillMaxWidth(),
                isError = state.languageError != null,
                supportingText = { state.languageError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            // Checkbox para saber si es artificial o no
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

            Spacer(modifier = Modifier.height(24.dp))

            // Botonera
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = events.onCancel,
                    enabled = !state.isSaving
                ) {
                    Text(stringResource(R.string.cancel))
                }

                Button(
                    onClick = events.onCreate,
                    enabled = !state.isSaving
                ) {
                    Text(stringResource(R.string.create))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(name = "1. Formulario Vacío (Light)", showBackground = true)
@Preview(
    name = "2. Formulario Vacío (Dark)",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAddSpeciesAdvanced() {
    StarWarsEntityManagementSystemTheme {
        AddSpeciesContent(
            modifier = Modifier,
            state = AddSpeciesState(),
            events = AddSpeciesEvents({}, {}, {}, {}, {}, {}, {}, {})
        )
    }
}