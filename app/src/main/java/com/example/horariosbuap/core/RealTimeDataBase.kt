package com.example.horariosbuap.ui.theme.dataBase

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.horariosbuap.model.*
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun getProfesores(datosViewModel : DatosViewModel){

    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Profesores")
    val profesores = ArrayList<Profesores?>()


    realTimeRef.addValueEventListener(object  : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){
                profesores.add(snapshot.getValue(Profesores::class.java))
            }
            datosViewModel.llenarProfesores(profesores)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}

fun getEdificios(datosViewModel: DatosViewModel){

    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Edificios").child("edificios")
    val edificios = ArrayList<Edificios?>()


    realTimeRef.addValueEventListener(object  : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){
                edificios.add(snapshot.getValue(Edificios::class.java))
            }
            datosViewModel.llenarEdificios(edificios)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}

fun getSalones(datosViewModel: DatosViewModel){

    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Edificios").child("salones")
    val salones = ArrayList<Salones?>()


    realTimeRef.addValueEventListener(object  : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){
                salones.add(snapshot.getValue(Salones::class.java))
            }
            datosViewModel.llenarSalones(salones)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}

fun getMaterias(datosViewModel: DatosViewModel){
    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Materias").child("materias_unicas").orderByChild("nombre")
    val materias = ArrayList<Materias?>()


    realTimeRef.addValueEventListener(object  : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){

                materias.add(snapshot.getValue(Materias::class.java))
            }
            datosViewModel.llenarMaterias(materias)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}

fun getMateriasHorario (datosViewModel: DatosViewModel){
    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Materias").child("materias_horario")
    val materias = ArrayList<MateriasHorario?>()


    realTimeRef.addValueEventListener(object  : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){

                materias.add(snapshot.getValue(MateriasHorario::class.java))
            }
            datosViewModel.llenarMateriasHorario(materias)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}

fun getHorarios(
    userDataViewModel: UserDataViewModel
){
    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Usuarios").child("materias_horario")
    val materias = ArrayList<MateriasHorario?>()


    realTimeRef.addValueEventListener(object  : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){

                materias.add(snapshot.getValue(MateriasHorario::class.java))
            }
//            userDataViewModel.llenarMateriasHorario(materias)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}

fun getNoticias(
    datosViewModel : DatosViewModel,
    context : Context
) {
    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Noticias")
    val noticias = ArrayList<News?>()

    realTimeRef.addValueEventListener(object  : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (snapshot in dataSnapshot.children){
                noticias.add(snapshot.getValue(News::class.java))
            }
            datosViewModel.llenarNoticias(noticias)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("OUT", "loadPost:onCancelled", error.toException())
            Toast.makeText(context, "Error al cargar, verifique su conexion a internet", Toast.LENGTH_LONG ).show()
        }

    })
}
