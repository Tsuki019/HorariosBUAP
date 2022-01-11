package com.example.horariosbuap.ui.theme.dataBase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.model.UserDB
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val DATABASE = FirebaseFirestore.getInstance().collection("Users")

fun setNuevoUsuario(
//    userDataViewModel: UserDataViewModel,
    userId : String,
    correo : String,
    provider : String
){
    val user  = UserDB(numHorarios = 0, correo = correo, provider = provider)
    var tempData : UserDB? = UserDB()

    if (provider == "GOOGLE"){
        DATABASE.document(userId).get().addOnCompleteListener {task->
            tempData = task.result.toObject(UserDB::class.java)
            if (tempData == null){
                DATABASE.document(userId).set(user)
            }
        }
    }else{
        DATABASE.document(userId).set(user)
    }
}

fun getUserData(
    userDataViewModel: UserDataViewModel,
    userId : String
){
    var user : UserDB? = UserDB()
    val nombres : ArrayList<String> = ArrayList()


    DATABASE.document(userId).get().addOnSuccessListener {document ->
        user = document.toObject(UserDB::class.java)
    }.addOnCompleteListener {
        userDataViewModel.isUserDataLoaded.value = false
        userDataViewModel.agregarUserData(user!!)
        if (user != UserDB()){
            DATABASE.document(userId).collection("horarios").get().addOnSuccessListener { query ->

                println("+++++++++++++++ QUERY NOMBRE : ${query.size()} +++++++++++++++++")
                for (document in query){
                    nombres.add(document.id)
                }
                userDataViewModel.fillNombresHorarios(nombres)
            }
        }
    }
}

fun getMateriasUnicas(
    nombreHorario: String,
    userDataViewModel: UserDataViewModel,
    userId: String
) {
    val materias : ArrayList<Materias> = ArrayList()

    if (userDataViewModel.isUserDataLoaded.value){
        userDataViewModel.isMateriasUnicasFill.value = false

        DATABASE.document(userId).collection("horarios").document(nombreHorario).collection("materiasUnicas").get().addOnSuccessListener { query ->
            if  (query.size() > 0){
                for(document in query){
                    materias.add(document.toObject(Materias::class.java))
                }
            }

            userDataViewModel.agregarMateriasUnicas(nombre = nombreHorario, materias = materias)
        }.addOnFailureListener { exception ->
            println(">>>>>>>>>UNICAS> $exception")
        }
    }
}

fun getMateriasHorario(
    nombreHorario: String,
    userDataViewModel: UserDataViewModel,
    userId: String
){
    val materias : ArrayList<MateriasHorario> = ArrayList()

    if (userDataViewModel.isUserDataLoaded.value){
        userDataViewModel.isMateriasHorarioFill.value = false

        DATABASE.document(userId).collection("horarios").document(nombreHorario).collection("materiaHorarios").get().addOnCompleteListener { task ->
            if  (task.result.size() > 0){
                for(document in task.result){
                    materias.add(document.toObject(MateriasHorario::class.java))
                }
            }
            userDataViewModel.agregarMateriasHorario(nombre = nombreHorario, materias = materias)
        }.addOnFailureListener { exception ->
            Log.w("MyErrors", "Error getMateriasHorario" )
        }
    }
}

fun setMateriaUnica(
    materia : Materias,
    nombreHorario : String,
    userId: String,
    userDataViewModel: UserDataViewModel,
    activity: Activity
){

    DATABASE.document(userId).collection("horarios").document(nombreHorario).collection("materiasUnicas").add(materia)
        .addOnCompleteListener {
            userDataViewModel.agregarMateria(nombreHorario = nombreHorario, materia = materia)
        }.addOnFailureListener { Toast.makeText(activity, "Error al agregar materia.", Toast.LENGTH_SHORT).show() }
    Toast.makeText(activity, "Agregada con exito!", Toast.LENGTH_SHORT).show()
}

fun setMateriaHorarios(
    horarios : ArrayList<MateriasHorario>,
    nombreHorario : String,
    userId: String,
    userDataViewModel: UserDataViewModel
){
    horarios.forEach{horario ->
        DATABASE.document(userId).collection("horarios").document(nombreHorario).collection("materiaHorarios").add(horario)
            .addOnFailureListener { Log.w("MyError", "Error agregarMateriaHorarios") }
    }
    userDataViewModel.agregarHorariosDeMateria(nombreHorario = nombreHorario, materiaHorarios = horarios)
}

fun crearHorario(
    nombre : String,
    userId: String,
    userDataViewModel: UserDataViewModel
){
    DATABASE.document(userId).collection("horarios").document(nombre).set(
        hashMapOf("nombre" to nombre)
    ).addOnCompleteListener{
        DATABASE.document(userId).update("numHorarios", userDataViewModel.userData.value.numHorarios + 1).addOnCompleteListener {
            userDataViewModel.agregarNombreHorario(nombre = nombre)
            userDataViewModel.userData.value = userDataViewModel.userData.value.copy(
                numHorarios = userDataViewModel.userData.value.numHorarios + 1
            )
        }
    }
}

fun eliminarHorario(
    nombre : String,
    userDataViewModel: UserDataViewModel
){
    val user = FirebaseAuth.getInstance().currentUser
    DATABASE.document(user!!.uid).collection("horarios").document(nombre).delete().addOnCompleteListener {
        DATABASE.document(user.uid).update(hashMapOf("numHorarios" to (userDataViewModel.userData.value.numHorarios-1)) as Map<String, Any>).addOnCompleteListener {
            userDataViewModel.userData.value = userDataViewModel.userData.value.copy(numHorarios = userDataViewModel.userData.value.numHorarios - 1)
            userDataViewModel.eliminarHorario(nombre = nombre)
        }
    }
}