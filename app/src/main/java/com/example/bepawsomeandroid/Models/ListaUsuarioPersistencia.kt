package com.example.bepawsomeandroid.Models

class ListaUsuariosPersistencia {
    val usuariosList: MutableList<Usuario> = mutableListOf()

    init {
        usuariosList.add(Usuario("Admin", "admin.gmail@gmail.com","default","Admin" ))
        usuariosList.add(Usuario("Tester", "tester.gmail@gmail.com","default","Tester" ))
        usuariosList.add(Usuario("Pepe", "pepe.gmail@gmail.com","default","Pepe" ))
        usuariosList.add(Usuario("Pablo", "palbo.gmail@gmail.com","default","Admin" ))
    }

    fun encontrarUsuario(nombre: String, password: String): Usuario?{
        var index = 0
        var usuarioEncontrado: Usuario? = null

        while (index < usuariosList.size && usuarioEncontrado == null) {
            val usuario = usuariosList[index]
            if (usuario.nombre == nombre && usuario.password == password) {
                usuarioEncontrado = usuario
            }
            index++
        }
        return usuarioEncontrado
    }

    fun obtenerTester():Usuario{
        return usuariosList[1];
    }
}