package com.example.horariosbuap.ui.theme.dataBase

import androidx.annotation.StringRes

data class LoginState (
    val email: String ="",
    val password: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
