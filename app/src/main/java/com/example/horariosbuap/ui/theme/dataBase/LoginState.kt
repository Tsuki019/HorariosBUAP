package com.example.horariosbuap.ui.theme.dataBase

import androidx.annotation.StringRes

data class LoginState (
    val name: String = "",
    val email: String ="",
    val password: String = "",
    val image: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
