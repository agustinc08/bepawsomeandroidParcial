package com.example.bepawsomeandroid.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.bepawsomeandroid.Models.Usuario


@Dao
interface UsuarioDao{
    @Insert
    suspend fun insertarUsuario(usuario: Usuario)


}




