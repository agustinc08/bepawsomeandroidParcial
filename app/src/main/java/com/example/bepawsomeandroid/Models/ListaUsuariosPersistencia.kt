package com.example.bepawsomeandroid.Models

data class ListaUsuariosPersistencia(
    val usuariosList: MutableList<User> = mutableListOf()
) {
    init {
        usuariosList.add(User("Admin", "admin.gmail@gmail.com", "Admin", "default", "123456"))
        usuariosList.add(User("Tester", "tester@gmail.com", "Tester", "default", "234567"))
        usuariosList.add(User("Pepe", "pepe@gmail.com", "Pepe", "default", "345678"))
        usuariosList.add(User("Pablo", "pablo@gmail.com", "Pablo", "default", "456789"))
    }

    fun encontrarUsuario(nombre: String, password: String): User? {
        return usuariosList.find { it.name == nombre && it.password == password }
    }

    fun obtenerTester(): User {
        return usuariosList[1]
    }
}