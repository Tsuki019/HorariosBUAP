package com.example.horariosbuap.ui.theme.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MateriasDao {
    @Query("SELECT * FROM materias")
    fun getAll() : LiveData<List<MateriaTabla>>

    @Query("SELECT * FROM materias WHERE nrc = :id")
    fun get(id : Int) : LiveData<MateriaTabla>

    @Insert
    fun insertAll(vararg materias:MateriaTabla)

    @Update
    fun update(materia : MateriaTabla)
}