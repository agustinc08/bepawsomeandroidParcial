package com.example.bepawsomeandroid.Models

data class ListaUsuariosPersistencia(
    val usuariosList: MutableList<Usuario> = mutableListOf()
) {
    init {
        usuariosList.add(Usuario("Admin", "admin.gmail@gmail.com", "default", "Admin", "123456"))
        usuariosList.add(Usuario("Tester", "tester@gmail.com", "default", "Tester", "234567"))
        usuariosList.add(Usuario("Pepe", "pepe@gmail.com", "default", "Pepe", "345678"))
        usuariosList.add(Usuario("Pablo", "pablo@gmail.com", "default", "Admin", "456789"))
    }

    fun encontrarUsuario(nombre: String, password: String): Usuario? {
        return usuariosList.find { it.nombre == nombre && it.password == password }
    }

    fun obtenerTester(): Usuario {
        return usuariosList[1]
    }
}