package com.example.bepawsomeandroid.Models

<<<<<<< HEAD
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
=======
data class Animal(
    val nombre: String = "",
    val ubicacion: String = "",
    val sexo: String = "",
    val peso: Double = 0.0,
    val edad: Int = 0,
    val raza: String = "",
    val subRaza: String = "",
    val imgUrl1: String = "",
    val imgUrl2: String = "",
    val imgUrl3: String = "",
    val imgUrl4: String = "",
)
>>>>>>> develop
