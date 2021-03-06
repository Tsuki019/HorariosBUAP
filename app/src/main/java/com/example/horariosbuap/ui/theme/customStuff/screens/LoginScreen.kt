package com.example.horariosbuap.ui.theme.customStuff.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.EventDialog
import com.example.horariosbuap.ui.theme.customStuff.components.OutlinedMediaButton
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.model.LoginState
import com.example.horariosbuap.ui.theme.*
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.viewmodel.RegisterViewModel


@Composable
fun LoginScreen(
    state: LoginState,
    registerViewModel: RegisterViewModel,
    onLogin: (String, String, Activity) -> Unit,
    onLoginWithGoogle: (Activity) -> Unit,
    activity: Activity,
    onNavigateToRegister: () -> Unit,
    onDissmisDialog: () -> Unit,
    onForgetPassword : () -> Unit
) {

    val emailValue = rememberSaveable{ mutableStateOf("")}
    val passwordValue = rememberSaveable{ mutableStateOf("")}
    var passwordVisibility by remember {mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    registerViewModel.state.value = registerViewModel.state.value.copy(successRegister = false)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = dark_blue1),
        contentAlignment = Alignment.TopCenter)
    {

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter)
        {
            ConstraintLayout {

                val surface = createRef()

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .constrainAs(surface) {
                            bottom.linkTo(parent.bottom)
                        },
                    color = MaterialTheme.colors.surface.copy(0.7f),
                    
                    shape = RoundedCornerShape(
                        topEndPercent = 8,
                        topStartPercent = 8,
                        bottomEndPercent = 8,
                        bottomStartPercent = 8
                    )
                ){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                           verticalArrangement = Arrangement.spacedBy(14.dp),
                           horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Text(
                            text = "Bienvenido",
                            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium),
                            fontFamily = sansPro,
                            color = MaterialTheme.colors.primary,
                        )
                        Text(text = "Ingresa a tu cuenta",
                             style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium),
                             fontFamily = sansPro,
                             color = MaterialTheme.colors.primary
                        )
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.spacedBy(8.dp))
                        {

                            TransparentTextField(
                                textFieldValue = emailValue,
                                textLabel = "Correo",
                                keyboardType = KeyboardType.Email,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                ),
                                imeAction = ImeAction.Next
                            )

                            TransparentTextField(
                                textFieldValue = passwordValue,
                                textLabel = "Contrase??a",
                                keyboardType = KeyboardType.Password,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.clearFocus()

                                        onLogin(emailValue.value, passwordValue.value, activity)
                                    }
                                ),
                                imeAction = ImeAction.Done,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        passwordVisibility = !passwordVisibility}
                                    ){
                                        Icon(
                                            imageVector = if(passwordVisibility){ Icons.Rounded.Visibility}
                                            else{Icons.Rounded.VisibilityOff},
                                            contentDescription = "",
                                            tint = MaterialTheme.colors.secondary)
                                    }
                                },
                                visualTransformation =
                                if (passwordVisibility){ VisualTransformation.None}
                                else {PasswordVisualTransformation()}
                            )
                            ClickableText(
                                modifier = Modifier.fillMaxWidth().wrapContentSize(align = Alignment.BottomEnd),
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colors.onPrimary,
                                            fontSize = 16.sp
                                        ),
                                    ){
                                        append("??Olvidaste tu contrase??a?")
                                    }}
                            )
                            {
                                onForgetPassword()
                            }
                        }

                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RoundedButton(
                                text = "Ingresar",
                                displayProgressBar = state.displayProgressBar,
                                onClick = {
                                    onLogin(emailValue.value, passwordValue.value, activity)
                                })
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Divider(modifier = Modifier.width(24.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colors.primary)

                                Text(modifier = Modifier.padding(8.dp),
                                     text = "O",
                                     style = MaterialTheme.typography.h6.copy(
                                         fontWeight = FontWeight.Black
                                     ),
                                    color = MaterialTheme.colors.primary,
                                    fontFamily = sansPro
                                )
                                Divider(modifier = Modifier.width(24.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colors.primary
                                )
                            }

                            Text(modifier = Modifier.fillMaxWidth(),
                                 text = "Ingresar con",
                                 style = MaterialTheme.typography.body1.copy(
                                     color = MaterialTheme.colors.primary
                                 ),
                                 textAlign = TextAlign.Center,
                                fontFamily = sansPro
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(modifier = Modifier.fillMaxWidth(),
                                   verticalArrangement = Arrangement.spacedBy(8.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                OutlinedMediaButton(text = "Ingresar con Google",
                                                    onClick = {
                                                            onLoginWithGoogle(activity)
                                                    },
                                                    buttonColor = RedGoogle
                                )
                                ClickableText(
                                    modifier = Modifier.padding(top = 10.dp),
                                    style = TextStyle(
                                        color = MaterialTheme.colors.primary,
                                        fontFamily = sansPro
                                    ),
                                    text = buildAnnotatedString { append("??No tienes una cuenta? ")
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colors.onPrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        ){
                                            append("Crear nueva cuenta.")
                                        }}
                                )
                                {
                                    onNavigateToRegister()
                                }
                            }

                        }
                    }
                }
            }
        }

        if (state.errorMessage != null){
            EventDialog(
                errorMessage = state.errorMessage,
                onDismiss = onDissmisDialog
            )
        }
    }
}


//@Preview
//@Composable
//fun TextLoginScreen() {
//
//    val viewModel: LoginViewModel = hiltViewModel()
//
//    val navController = rememberNavController()
//    LoginScreen(state = viewModel.state.value,
//                onLogin = viewModel::login,
//                onDissmisDialog = viewModel::hideErrorDialog,
//                onNavigateToRegister = {navController.navigate(route = MainDestinations.REGISTRATION_ROUTE)}
//    )
//}