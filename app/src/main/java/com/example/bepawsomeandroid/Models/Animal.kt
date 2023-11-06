package com.example.bepawsomeandroid.Models

class Animal(
    var nombre: String = "",
    var ubicacion: String = "",
    var sexo: String = "",
    var peso: Double = 0.0,
    var edad: Int = 0,
    var raza: String = "",
    var imgUrl1: String = "",
    var imgUrl2: String = "",
    var imgUrl3: String = "",
    var imgUrl4: String = ""
) {
    // Constructor sin argumentos necesario para Firebase
    constructor() : this("", "", "", 0.0, 0, "", "", "", "", "")
}