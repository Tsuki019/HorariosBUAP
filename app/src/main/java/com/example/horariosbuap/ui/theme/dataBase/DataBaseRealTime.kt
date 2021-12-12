package com.example.horariosbuap.ui.theme.dataBase

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

    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Edificios")
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

fun getClases(){}

fun buscarPorKey(){
    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Profesores")
    val profesores : ArrayList<Profesores?> = arrayListOf()

    realTimeRef.addValueEventListener(object  : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for (snapshot in dataSnapshot.children){
                profesores.add(snapshot.getValue(Profesores::class.java))
            }
            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
//            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
}