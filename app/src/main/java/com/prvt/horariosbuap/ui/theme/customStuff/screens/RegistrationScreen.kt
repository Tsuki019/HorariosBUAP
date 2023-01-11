package com.prvt.horariosbuap.ui.theme.customStuff.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.prvt.horariosbuap.ui.theme.customStuff.components.EventDialog
import com.prvt.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.prvt.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.prvt.horariosbuap.viewmodel.LoginViewModel
import com.prvt.horariosbuap.model.RegisterState
import com.prvt.horariosbuap.ui.theme.customStuff.sansPro
import com.prvt.horariosbuap.ui.theme.dark_blue1
import com.prvt.horariosbuap.viewmodel.UserDataViewModel


@Composable
fun RegistrationScreen(
    state: RegisterState,
    loginViewModel: LoginViewModel,
    userDataViewModel: UserDataViewModel,
    onRegister: (String, String, String, String, Activity, LoginViewModel, UserDataViewModel) -> Unit,
    onBack: () -> Unit,
    activity: Activity,
    onDismissDialog: () -> Unit
) {

    val nameValue = remember { mutableStateOf("")}
    val emailValue = remember { mutableStateOf("")}
    val passwordValue = remember { mutableStateOf("")}
    val confirmPasswordValue = remember { mutableStateOf("")}
    var passwordVisibility by remember {mutableStateOf(false)}
    var confirmPasswordVisibility by remember {mutableStateOf(false)}

    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = dark_blue1))
    {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter)
        {
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            color = MaterialTheme.colors.surface.copy(alpha = 0.7f),
            shape = RoundedCornerShape(
                topStartPercent = 8,
                topEndPercent = 8,
                bottomStartPercent = 8,
                bottomEndPercent = 8)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()))
                {
                    Row(verticalAlignment = Alignment.CenterVertically, )
                    {
                        IconButton(onClick = { onBack() })
                        {
                            Icon(imageVector = Icons.Rounded.ArrowBack,
                                 contentDescription = "back icon",
                                 tint = MaterialTheme.colors.primary)
                        }

                        Text(modifier = Modifier.padding(start = 15.dp),
                             text = "Crea una cuenta",
                             style = MaterialTheme.typography.h5.copy(
                                 color = MaterialTheme.colors.primary)
                        )
                    }

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(top = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp))
                    {
                        TransparentTextField(
                            textFieldValue = nameValue,
                            textLabel = "Nombre",
                            keyboardType = KeyboardType.Text,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Down
                                    )
                                }
                            ),
                            imeAction = ImeAction.Next
                        )

                        TransparentTextField(
                            textFieldValue = emailValue,
                            textLabel = "Correo",
                            keyboardType = KeyboardType.Email,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Down
                                    )
                                }
                            ),
                            imeAction = ImeAction.Next
                        )

                        TransparentTextField(
                            textFieldValue = passwordValue,
                            textLabel = "Contraseña",
                            keyboardType = KeyboardType.Password,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Down
                                    )
                                }
                            ),
                            imeAction = ImeAction.Next,
                            trailingIcon = {
                                IconButton(onClick = {passwordVisibility = !passwordVisibility})
                                {
                                    Icon(
                                        imageVector = if (passwordVisibility) Icons.Rounded.Visibility
                                        else Icons.Rounded.VisibilityOff,
                                        contentDescription = ""
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisibility) VisualTransformation.None
                                                    else PasswordVisualTransformation()

                        )

                        TransparentTextField(
                            textFieldValue = confirmPasswordValue,
                            textLabel = "Confirmar contraseña",
                            keyboardType = KeyboardType.Password,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onRegister(
                                        nameValue.value,
                                        emailValue.value,
                                        passwordValue.value,
                                        confirmPasswordValue.value,
                                        activity,
                                        loginViewModel,
                                        userDataViewModel
                                    )
                                }
                            ),
                            imeAction = ImeAction.Done,
                            trailingIcon = {
                                IconButton(onClick = {confirmPasswordVisibility = !confirmPasswordVisibility})
                                {
                                    Icon(
                                        imageVector = if (confirmPasswordVisibility) Icons.Rounded.Visibility
                                        else Icons.Rounded.VisibilityOff,
                                        contentDescription = ""
                                    )
                                }
                            },
                            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        RoundedButton(text = "Registrarse",
                                      displayProgressBar = state.displayProcessbar,
                                      onClick = {
                                          onRegister(
                                              nameValue.value,
                                              emailValue.value,
                                              passwordValue.value,
                                              confirmPasswordValue.value,
                                              activity,
                                              loginViewModel,
                                              userDataViewModel
                                          )
                                      })

                        ClickableText(
                            modifier = Modifier.padding(top = 10.dp),
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontFamily = sansPro
                            ),
                            text = buildAnnotatedString {append("¿Ya tienes una cuenta? ")
                            withStyle(style = SpanStyle(
                                color= MaterialTheme.colors.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontFamily = sansPro))
                            {
                                append("Inicia Sesion")
                            } },
                                      onClick = {
                                          onBack()
                                      }
                        )
                    }
                }
                

            }
        }

        if(state.errorMessage != null){
            EventDialog(errorMessage = state.errorMessage, onDismiss = onDismissDialog )
        }

    }
}
