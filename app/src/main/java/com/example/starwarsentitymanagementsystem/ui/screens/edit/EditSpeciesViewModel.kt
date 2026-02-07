package com.example.starwarsentitymanagementsystem.ui.screens.edit

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

@HiltViewModel
class EditSpeciesViewModel @Inject constructor(
    private val repository: SpeciesRepository
) : ViewModel() {

    var state by mutableStateOf(EditSpeciesState())
        private set

    // Carga inicial de datos
    fun setSpecies(species: Species) {
        state = state.copy(
            id = species.speciesId,
            name = species.name,
            classification = species.classification,
            designation = species.designation,
            language = species.language,
            discoveryDate = species.discoveryDate ?: "",
            description = species.description ?: "",
            isArtificial = species.isArtificial
        )
    }

    // Eventos de cambios en inputs
    fun onNameChange(v: String) { state = state.copy(name = v, nameError = null) }
    fun onClassificationChange(v: String) { state = state.copy(classification = v, classificationError = null) }
    fun onDesignationChange(v: String) { state = state.copy(designation = v) }
    fun onLanguageChange(v: String) { state = state.copy(language = v, languageError = null) }
    fun onDiscoveryDateChange(v: String) { state = state.copy(discoveryDate = v, discoveryDateError = null) }
    fun onIsArtificialChange(v: Boolean) { state = state.copy(isArtificial = v) }

    // Gestión del diálogo de borrado
    fun showDeleteConfirmation() { state = state.copy(showDeleteDialog = true) }
    fun dismissDeleteDialog() { state = state.copy(showDeleteDialog = false) }

    fun validateForm(): Boolean {
        var isValid = true
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
        // Validación fecha
        if (state.discoveryDate.isNotBlank() && !state.discoveryDate.matches(dateRegex)) {
            state = state.copy(discoveryDateError = "Formato incorrecto (DD/MM/AAAA)")
            isValid = false
        }

        return isValid
    }

    fun onUpdateSpecies() {
        if (!validateForm()) return

        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isSaving = true)

            val updatedSpecies = Species(
                speciesId = state.id,
                name = state.name,
                classification = state.classification,
                designation = state.designation,
                language = state.language,
                discoveryDate = state.discoveryDate,
                description = state.description, // Conservamos el dato
                isArtificial = state.isArtificial
            )

            repository.updateSpecies(updatedSpecies)
            state = state.copy(isSaving = false, isOperationSuccess = true)
        }
    }

    fun onDeleteSpecies() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isSaving = true, showDeleteDialog = false)

            val speciesToDelete = Species(
                speciesId = state.id,
                name = state.name,
                classification = "",
                designation = "",
                language = "",
                description = "",
                discoveryDate = "",
                isArtificial = false
            )

            repository.deleteSpecies(speciesToDelete)
            state = state.copy(isSaving = false, isOperationSuccess = true)
        }
    }
}