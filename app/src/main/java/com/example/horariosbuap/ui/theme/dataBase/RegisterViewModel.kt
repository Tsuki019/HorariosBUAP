package com.example.horariosbuap.ui.theme.dataBase

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horariosbuap.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel : ViewModel () {
    val state: MutableState<RegisterState> = mutableStateOf(RegisterState())

    fun register(name: String, email: String, password: String, confirmPassword: String){
        val errorMessage = if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            R.string.error_input_empty
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_email
        }else if(password != confirmPassword){
            R.string.error_incorrectly_repeated_password
        }else null

        errorMessage.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            //SIMULACION DE LOGIN
            state.value = state.value.copy(displayProcessbar = true)
            delay(2000)
            state.value = state.value.copy(displayProcessbar = false)
        }
    }

    fun hideErrorDialog(){
        state.value = state.value.copy(errorMessage = null)
    }
}