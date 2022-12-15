package com.example.horariosbuap.viewmodel

import android.app.Activity
import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horariosbuap.R
import com.example.horariosbuap.model.LoginState
import com.example.horariosbuap.ui.theme.dataBase.getUserData
import com.example.horariosbuap.ui.theme.dataBase.setNuevoUsuario
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    val state: MutableState<LoginState> = mutableStateOf(LoginState())
    val tempImage : MutableState<Uri> = mutableStateOf(Uri.EMPTY)

    fun login(email: String, password: String, activity: Activity, userDataViewModel: UserDataViewModel){
        val errorMessage = if (email.isBlank() || password.isBlank()){
            R.string.error_input_empty
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_email
        }else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {

            state.value = state.value.copy(displayProgressBar = true)

            loginWithEmail(
                email = email,
                password = password,
                activity = activity,
                userDataViewModel = userDataViewModel
            )

//            state.value = state.value.copy(email = email, password = password)
            state.value = state.value.copy(displayProgressBar = false)
//            state.value = state.value.copy(successLogin = true)
        }
    }

    fun loginWithEmail(email: String, password: String, activity: Activity, userDataViewModel: UserDataViewModel){
        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                    if(!user!!.isEmailVerified){
                        state.value = state.value.copy(errorMessage = R.string.error_email_verify)
                        Firebase.auth.signOut()
                    }else{
                        llenarDatosUsuario(user = user, userDataViewModel)
                    }
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    updateUI(null)
                    when (task.exception!!.message){

                        "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                            state.value = state.value.copy(errorMessage = R.string.error_email_no_registrado)
                        }
                        "The password is invalid or the user does not have a password." -> {
                            state.value = state.value.copy(errorMessage = R.string.error_incorrect_values)
                        }
                        else -> {
                            state.value = state.value.copy(errorMessage = R.string.error_login_email)
                        }

                    }
                    println("xxxxxxxxxxxxxxxxx${task.exception!!.message}xxxxxxxxxxxxxxxxxx")
                }
            }
    }

    fun loginWithGoogle(activity : Activity){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.my_default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)

        val signInIntent = googleSignInClient.signInIntent

        activity.startActivityForResult(signInIntent, 1)

    }

    fun hideErrorDialog(){
        state.value = state.value.copy(errorMessage = null)
    }

    fun finishLogin(accountTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = accountTask.getResult(ApiException::class.java)

            state.value = state.value.copy(displayProgressBar = true)

//            println("======${account}======")
            account?.idToken?.let { token ->
                val auth = FirebaseAuth.getInstance()
                val credential =  GoogleAuthProvider.getCredential(token, null)

                auth.signInWithCredential(credential)
                    .addOnCompleteListener{task ->
                        state.value = state.value.copy(displayProgressBar = false)
                        if (task.isSuccessful){

                            state.value = state.value.copy(
                                name = auth.currentUser!!.displayName!!,
                                email = auth.currentUser!!.email!!,
                                image = auth.currentUser!!.photoUrl.toString(),
                                successLogin = true)
                            setNuevoUsuario(userId = auth.currentUser!!.uid, auth.currentUser!!.email!!, provider = "GOOGLE")
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
            println("======$e======")
            return
        }

    }

    fun  llenarDatosUsuario(
        user : FirebaseUser,
        userDataViewModel: UserDataViewModel
    ){
        state.value = state.value.copy(name = user.displayName!!,
                                       email = user.email!!,
                                       image = user.photoUrl.toString(),
                                       successLogin = true)
        getUserData(userDataViewModel, user.uid)
    }
}