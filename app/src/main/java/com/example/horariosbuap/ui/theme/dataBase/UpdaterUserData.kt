package com.example.horariosbuap.ui.theme.dataBase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.horariosbuap.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun UpdateUserAvatar(
    viewModel: LoginViewModel,
    cambiarImagenState: MutableState<Boolean>,
    progressBarState: MutableState<Boolean>
){

    val user =Firebase.auth.currentUser
    val storageRef =  Firebase.storage.reference
    val avatarImagesRef = storageRef.child("Avatars/${viewModel.state.value.email}.jpg")

    if (viewModel.tempImage != Uri.EMPTY){
        avatarImagesRef.putFile(viewModel.tempImage.value).addOnSuccessListener {
            avatarImagesRef.downloadUrl.addOnSuccessListener {
                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(it.toString())
                }
                user!!.updateProfile(profileUpdates).addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        viewModel.state.value = viewModel.state.value.copy(image = user.photoUrl.toString())
                    }
                }

                cambiarImagenState.value = false
                progressBarState.value = false

            }.addOnFailureListener {
                viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_download_image)
                cambiarImagenState.value = false
                progressBarState.value = false
            }

        }.addOnFailureListener{
            viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_upload_image)
            cambiarImagenState.value = false
            progressBarState.value = false
        }
    }else{
        cambiarImagenState.value = false
        progressBarState.value = false
    }
}

fun SelectImage(activity : Activity){

    val intent = Intent()

    intent.type = "image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    activity.startActivityForResult(intent, 100)
}

fun UpdateUserName(
    newName: String,
    viewModel: LoginViewModel,
    activity: Activity
){
    val user =Firebase.auth.currentUser

    if (user != null){

        val profileUpdates = userProfileChangeRequest {
            displayName = newName
        }

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.state.value = viewModel.state.value.copy(
                        name = user.displayName!!,
                    )
                    Toast.makeText(activity, "Nombre guardado", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_update_user_data)
            }
//        progressBarState.value = false
    }
}

fun UpdateUserPassword(
    newPassword : String,
    viewModel: LoginViewModel,
    activity: Activity,
    user : FirebaseUser
) {

    user.updatePassword(newPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Se ha actualizado la contraseña con exito!", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_update_user_data)
            }
        }.addOnFailureListener{
            viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_update_user_data)
        }
}

fun LogOutUser(viewModel: LoginViewModel, userDataViewModel: UserDataViewModel){
    val auth = Firebase.auth

    if (auth.currentUser != null){
        auth.signOut()

        userDataViewModel.userData.value = userDataViewModel.userData.value.copy(numHorarios = 0, correo = "", provider = "")
        userDataViewModel.horarios.clear()
        userDataViewModel.isUserDataLoaded.value = false
        userDataViewModel.isMateriasHorarioFill.value = false
        userDataViewModel.isMateriasUnicasFill.value = false
        viewModel.state.value = viewModel.state.value.copy(
            name = "",
            email = "",
            password = "",
            image = "",
            successLogin = false,
            displayProgressBar = false,
            errorMessage = null
        )
    }
}
