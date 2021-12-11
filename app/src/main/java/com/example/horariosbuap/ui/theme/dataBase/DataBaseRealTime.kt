package com.example.horariosbuap.ui.theme.dataBase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun getProfesores() : ArrayList<Profesores?>{

    val realTimeRef = FirebaseDatabase.getInstance().reference.child("Profesores")
    val profesores = ArrayList<Profesores?>()

    realTimeRef.addValueEventListener(object  : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            println("==+++==${dataSnapshot.children}==+++==")
            for (snapshot in dataSnapshot.children){
                println("====${snapshot.getValue(Profesores::class.java)}=====")
                profesores.add(snapshot.getValue(Profesores::class.java))
                println("==::::==${profesores.size}==::::==")
            }
            return
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
//            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }

    })
    println("==3333:==${profesores.size}==333333::==")
    return profesores
}

fun getSanes(){}

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