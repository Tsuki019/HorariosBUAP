package com.example.horariosbuap.ui.theme.dataBase

import androidx.annotation.StringRes

data class RegisterState(
    val successRegister: Boolean = false,
    val displayProcessbar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
