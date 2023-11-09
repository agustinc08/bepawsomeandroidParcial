package com.example.bepawsomeandroid.Models

data class Animal(
    var nombre: String = "",
    var ubicacion: String = "",
    var sexo: String = "",
    var peso: Double = 0.0,
    var edad: Int = 0,
    var raza: String = "",
    var subraza: String = "",
    var usuarioId: String = "",

) {
    // Constructor sin argumentos necesario para Firebase
    constructor() : this("", "", "", 0.0, 0, "", "", "")
}

