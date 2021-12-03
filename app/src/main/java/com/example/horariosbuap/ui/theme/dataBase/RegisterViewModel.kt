package com.example.horariosbuap.ui.theme.dataBase

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horariosbuap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel : ViewModel () {
    val state: MutableState<RegisterState> = mutableStateOf(RegisterState())

    fun register(name: String, email: String, password: String, confirmPassword: String, activity:Activity, loginViewModel: LoginViewModel){

        val errorMessage = if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            R.string.error_input_empty
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_email
        }else if(password != confirmPassword){
            R.string.error_incorrectly_repeated_password
        }else if(password.length < 6){
            R.string.error_length_password
        }else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {

            state.value = state.value.copy(displayProcessbar = true)

            val auth = Firebase.auth

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        state.value = state.value.copy(displayProcessbar = false)
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        println("+++++++++SE REGISTRO EL USUARIO++++++++")
                        println("==========Email: ${auth.currentUser!!.email}============")
                        println("==========Nombre: ${auth.currentUser!!.displayName}============")
                        loginViewModel.state.value = loginViewModel.state.value.copy(
                            name = name,
                            email = email
                        )

                    } else {
                        state.value = state.value.copy(displayProcessbar = false)

                        if (task.exception!!.message == "The email address is already in use by another account."){
                            state.value = state.value.copy(errorMessage = R.string.error_already_in_use_email)
                        }else{
                            state.value = state.value.copy(errorMessage = R.string.error_register_process)
                            Log.w(TAG, "createUserWithEmail: failure", task.exception)
                        }
                    }
                }
        }
    }

    fun hideErrorDialog(){
        state.value = state.value.copy(errorMessage = null)
    }
}