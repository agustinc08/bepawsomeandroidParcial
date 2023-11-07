package com.example.bepawsomeandroid.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: String,
    val nombre: String = "",
    val mail: String = "",
    val foto: String = "",
    val password: String = "",
    val telefono: String = ""
) {
    // Constructor sin argumentos necesario para Firebase
    constructor() : this("", "", "", "", "")
}