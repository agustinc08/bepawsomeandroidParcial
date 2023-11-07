package com.example.bepawsomeandroid.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bepawsomeandroid.Models.Usuario

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class roomDb : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}
