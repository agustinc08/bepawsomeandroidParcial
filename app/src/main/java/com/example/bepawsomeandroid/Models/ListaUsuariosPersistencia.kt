package com.example.bepawsomeandroid.Models

data class ListaUsuariosPersistencia(
    val usuariosList: MutableList<User> = mutableListOf()
) {
    init {
        usuariosList.add(User("Admin", "admin.gmail@gmail.com", "Admin", "https://icon-library.com/icon/google-user-icon-17.html", "123456"))
        usuariosList.add(User("Tester", "tester@gmail.com", "Tester", "https://icon-library.com/images/google-user-icon/google-user-icon-21.jpg", "234567"))
        usuariosList.add(User("Pepe", "pepe@gmail.com", "Pepe", "https://icon-library.com/icon/google-user-icon-18.html", "345678"))
        usuariosList.add(User("Pablo", "pablo@gmail.com", "Pablo", "https://icon-library.com/icon/google-user-icon-19.html", "456789"))
    }

    fun encontrarUsuario(nombre: String, password: String): User? {
        return usuariosList.find { it.name == nombre && it.password == password }
    }

    fun obtenerTester(): User {
        return usuariosList[1]
    }
}