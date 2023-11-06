package com.example.bepawsomeandroid.Models

class ListaUsuariosPersistencia {
    val usuariosList: MutableList<Usuario> = mutableListOf()

    init {
        usuariosList.add(Usuario("Admin", "admin.gmail@gmail.com","default","Admin",  "123456" ))
        usuariosList.add(Usuario("Tester", "tester.gmail@gmail.com","default","Tester",  "234567" ))
        usuariosList.add(Usuario("Pepe", "pepe.gmail@gmail.com","default","Pepe", "345678" ))
        usuariosList.add(Usuario("Pablo", "palbo.gmail@gmail.com","default","Admin","456789" ))
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