package com.example.starwarsentitymanagementsystem.ui.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsentitymanagementsystem.data.model.Species
import com.example.starwarsentitymanagementsystem.data.repository.SpeciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Estado ampliado con errores para feedback visual (Rúbrica: CD3.6, CD4.8)


@HiltViewModel
class AddSpeciesViewModel @Inject constructor(
    private val repository: SpeciesRepository
) : ViewModel() {

    var state by mutableStateOf(AddSpeciesState())
        private set

    // Controla si se ha guardado exitosamente para resetear la UI o navegar
    var isSaved by mutableStateOf(false)
        private set

    // Métodos simples para actualizar estado
    fun onNameChange(v: String) { state = state.copy(name = v, nameError = null) }
    fun onClassificationChange(v: String) { state = state.copy(classification = v, classificationError = null) }
    fun onDesignationChange(v: String) { state = state.copy(designation = v) }
    fun onLanguageChange(v: String) { state = state.copy(language = v, languageError = null) }
    fun onDiscoveryDateChange(v: String) { state = state.copy(discoveryDate = v, discoveryDateError = null) }
    fun onIsArtificialChange(v: Boolean) { state = state.copy(isArtificial = v) }
    fun onDismissDialog() { state = state.copy(showDuplicateError = false) }

    /**
     * Valida los datos antes de permitir guardar.
     * Retorna TRUE si todo está bien visualmente, FALSE si hay errores (pone textos en rojo).
     */
    fun validateForm(): Boolean {
        var isValid = true

        // Regex para DD/MM/AAAA
        val dateRegex = Regex("""^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\d{4}$""")

        if (state.name.length < 3) {
            state = state.copy(nameError = "Mínimo 3 caracteres")
            isValid = false
        }

        if (state.classification.isBlank()) {
            state = state.copy(classificationError = "Campo requerido")
            isValid = false
        }

        if (state.language.isBlank()) {
            state = state.copy(languageError = "Campo requerido")
            isValid = false
        }

        // VALIDACIÓN DE FECHA
        if (state.discoveryDate.isNotBlank() && !state.discoveryDate.matches(dateRegex)) {
            state = state.copy(discoveryDateError = "Formato inválido (DD/MM/AAAA)")
            isValid = false
        }

        return isValid
    }

    /**
     * Primero verifica duplicados en BD, si pasa, guarda.
     * Esta función debe llamarse SOLO si validateForm() dio true.
     */
    fun checkDuplicateAndSave(onSuccess: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(isSaving = true)

            // Validación de Integridad: NO DUPLICADOS
            val exists = repository.existsByName(state.name.trim())

            if (exists) {
                // Si existe, mostramos Dialog y paramos
                state = state.copy(isSaving = false, showDuplicateError = true)
            } else {
                // Si no existe, guardamos de verdad
                saveSpeciesToDb()
                onSuccess() // Callback para lanzar notificación
            }
        }
    }

    private suspend fun saveSpeciesToDb() {
        val newSpecies = Species(
            name = state.name,
            classification = state.classification,
            designation = state.designation,
            language = state.language,
            discoveryDate = state.discoveryDate,
            description = "Descripción genérica",
            isArtificial = state.isArtificial
        )
        repository.addSpecies(newSpecies)
        state = state.copy(isSaving = false)
        isSaved = true
    }

    fun resetState() {
        state = AddSpeciesState()
        isSaved = false
    }
}