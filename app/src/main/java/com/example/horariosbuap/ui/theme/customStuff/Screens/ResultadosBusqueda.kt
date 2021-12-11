package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.dataBase.DatosViewModel
import com.example.horariosbuap.ui.theme.dataBase.Profesores
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun ResultadosBusqueda(
    tipo : Int?,
    datosViewModel: DatosViewModel
) {

    when(tipo){

        1 -> {
            val realTimeRef = FirebaseDatabase.getInstance().reference.child("Profesores")
            val profesores = ArrayList<Profesores?>()


            realTimeRef.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    println("==+++==${dataSnapshot.children}==+++==")
                    for (snapshot in dataSnapshot.children){
                        profesores.add(snapshot.getValue(Profesores::class.java))
                        println("==::::==${profesores.size}==::::==")
                    }
                    datosViewModel.llenarProfesores(profesores)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }

            })
        }
        2 -> {}
        3 -> {}
        else -> {}
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), 
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars, 
                applyTop = false
            )
        ){
            item { println("====${!datosViewModel.profesores.isEmpty()}=====")
                if (datosViewModel.profesoresState.value){
                    for (datos in datosViewModel.profesores){
                        CardProfesor(datos = datos)
                    }
                }
            }
        }
    }
}

@Composable
fun CardProfesor(
    datos : Profesores?
) {
    println("#####${datos!!.nombre}#####")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = Color.White,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.azulOscuroInstitucional)),
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        )
    ) {
        val modifier = Modifier.padding(start = 5.dp)

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = modifier.padding(top = 3.dp),
                text = datos!!.nombre,
                style = MaterialTheme.typography.h5.copy(color = colorResource(id = R.color.azulOscuroInstitucional), fontWeight = FontWeight.Bold))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.azulOscuroInstitucional)
            )
            Text(
                modifier = modifier,
                text = datos.puesto,
                style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulOscuroInstitucional), fontWeight = FontWeight.Bold))
            if (datos.cubiculo != ""){
                Text(
                    modifier = modifier,
                    text = datos.cubiculo,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
            }
            if (datos.correo2 != ""){
                Text(
                    modifier = modifier,
                    text = datos.correo,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
                Text(
                    modifier = modifier.padding(bottom = 5.dp),
                    text = datos.correo2,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
            }else{
                Text(
                    modifier = modifier.padding(bottom = 5.dp),
                    text = datos.correo,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
            }
        }
    }
}