package com.example.horariosbuap.ui.theme.dataBase

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horariosbuap.R
//import com.example.horariosbuap.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    fun login(email: String, password: String){
        val errorMessage = if (email.isBlank() || password.isBlank()){
            R.string.error_input_empty
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_email
        }else if(email != "a@gmail.com" || password != "a"){
            R.string.error_incorrect_values
        }else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            //SIMULACION DE LOGIN
            state.value = state.value.copy(displayProgressBar = true)
            delay(2000)
            state.value = state.value.copy(email = email, password = password)
            state.value = state.value.copy(displayProgressBar = false)
            state.value = state.value.copy(successLogin = true)
        }
    }

    fun loginWithGoogle(activity : Activity){
        println("ENTRA A LOGIN CON GOOGLE")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("730691748527-4160a7njne9bc0dp8c81nt57vp5i3522.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        val signInIntent : Intent = mGoogleSignInClient.signInIntent

        activity.startActivityForResult(signInIntent, 1)

    }

    fun hideErrorDialog(){
        state.value = state.value.copy(errorMessage = null)
    }

    fun finishLogin(accountTask: Task<GoogleSignInAccount>) {
        println("ENTRA A finishLogin")
        try {
            val account: GoogleSignInAccount? = accountTask.getResult(ApiException::class.java)
            state.value = state.value.copy(displayProgressBar = true)

            account?.idToken?.let { token ->
                val auth = FirebaseAuth.getInstance()
                val credential =  GoogleAuthProvider.getCredential(token, null)

                auth.signInWithCredential(credential)
                    .addOnCompleteListener{task ->
                        state.value = state.value.copy(displayProgressBar = false)
                        if (task.isSuccessful){
                            val user =  auth.currentUser
                            val email = auth.currentUser!!.email
                            println("================>> $user <<===================")
                            println("================>> $email <<===================")

                            state.value = state.value.copy(successLogin = true)
                        }else{
                            state.value = state.value.copy(errorMessage = R.string.error_login_google)
                        }
                    }
            }
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            state.value = state.value.copy(errorMessage = R.string.error_login_google)
            state.value = state.value.copy(displayProgressBar = false)
            return
        }

    }
}