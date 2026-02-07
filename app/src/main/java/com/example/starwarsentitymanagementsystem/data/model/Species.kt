package com.example.starwarsentitymanagementsystem.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "species")
@Parcelize
data class Species(
    @PrimaryKey(autoGenerate = true)
    val speciesId: Int = 0,
    val name: String,
    val classification: String,
    val designation: String,
    val language: String,
    val description: String?,
    val discoveryDate: String?,
    val isArtificial: Boolean
) : Parcelable