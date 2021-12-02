package com.example.horariosbuap.ui.theme.customStuff.Screens

import android.graphics.Paint
import androidx.activity.OnBackPressedCallback
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.EventDialog
import com.example.horariosbuap.ui.theme.customStuff.components.OutlinedMediaButton
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.RegisterState
import com.example.horariosbuap.ui.theme.dataBase.RegisterViewModel


@Composable
fun RegistrationScreen(
    state: RegisterState,
    onRegister: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
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
        .background(color = colorResource(id = R.color.azulOscuroInstitucional)))
    {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter)
        {
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            color = colorResource(id = R.color.BlancoTransparente),
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
                                 tint = colorResource(id = R.color.azulOscuroInstitucional))
                        }

                        Text(modifier = Modifier.padding(start = 15.dp),
                             text = "Crea una cuenta",
                             style = MaterialTheme.typography.h5.copy(
                                 color = colorResource(id = R.color.azulOscuroInstitucional))
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
                                    onRegister(nameValue.value,
                                               emailValue.value,
                                               passwordValue.value,
                                               confirmPasswordValue.value
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
                                          onRegister(nameValue.value,
                                                     emailValue.value,
                                                     passwordValue.value,
                                                     confirmPasswordValue.value
                                          )
                                      })

                        ClickableText(text = buildAnnotatedString {append("¿Ya tienes una cuenta?")
                            withStyle(style = SpanStyle(color= colorResource(id = R.color.azulClaroInstitucional),
                                                        fontWeight = FontWeight.Bold))
                            {
                                append("Inicia Sesion")
                            } },
                                      onClick = {
                                          onBack()
                                      }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Divider(modifier = Modifier.width(24.dp),
                            thickness = 1.dp,
                            color = Color.Gray)

                            Text(modifier = Modifier.padding(8.dp),
                                 text = "O",
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.Black
                            ))

                            Divider(modifier = Modifier.width(24.dp),
                                    thickness = 1.dp,
                                    color = Color.Gray)
                        }

                        Text(modifier = Modifier.fillMaxWidth(),
                             text = "Ingresar con",
                             style = MaterialTheme.typography.body1.copy(
                                 color = colorResource(id = R.color.azulOscuroInstitucional)
                             ),
                            textAlign = TextAlign.Center)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        OutlinedMediaButton(text = "Ingresar con Google",
                                            onClick = { /*TODO("Realizar registro con color")*/ },
                                            buttonColor = colorResource(id = R.color.RojoGoogle))
                    }
                }
                

            }
        }

        if(state.errorMessage != null){
            EventDialog(errorMessage = state.errorMessage, onDismiss = onDismissDialog )
        }

    }

}


@Preview
@Composable
fun TestRegistrationScreen() {

    val viewModel: RegisterViewModel = hiltViewModel()
    RegistrationScreen(
        state = viewModel.state.value,
        onRegister = viewModel::register,
        onBack = { },
        onDismissDialog = viewModel::hideErrorDialog
    )
}