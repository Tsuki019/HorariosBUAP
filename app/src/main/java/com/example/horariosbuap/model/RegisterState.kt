package com.example.horariosbuap.model

import androidx.annotation.StringRes

data class RegisterState(
    val successRegister: Boolean = false,
    val displayProcessbar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
