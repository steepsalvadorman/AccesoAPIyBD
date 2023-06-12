package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.ulima.dbaccess.R
import pe.edu.ulima.dbaccess.ui.app.viewmodels.CreateAccountScreenViewModel
import pe.edu.ulima.dbaccess.ui.theme.Gray200
import pe.edu.ulima.dbaccess.ui.theme.Gray400
import pe.edu.ulima.dbaccess.ui.theme.Orange200


@Preview
@Composable
fun CreateAccountScreenPreview()
{
    CreateAccountScreen(
        CreateAccountScreenViewModel(),
    ) {}

}





@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountScreenViewModel,
    goToLoginScreen: () -> Unit
)
{
    val context = LocalContext.current
    // viewmodel
    val usuario : String by viewModel.usuario.observeAsState(initial = "")
    val correo : String by viewModel.correo.observeAsState(initial = "")
    val contrasenia : String by viewModel.contrasenia.observeAsState(initial = "")
    val repcontrasenia : String by viewModel.repcontrasenia.observeAsState(initial = "")
    val mensaje : String by viewModel.mensaje.observeAsState(initial = "")
    // close
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                val activity = context as Activity
                activity.finish()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        ){
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Person Icon",
            )
        }
    }
    // container
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_ulima),
                contentDescription = "Logo Ulima",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 10.dp),
                colorFilter = ColorFilter.tint(
                    color = if(isSystemInDarkTheme()) Orange200 else Gray200
                )
            )
            Text(
                text = "Crear Cuenta",
                textAlign = TextAlign.Center,
            )
            if(mensaje.contains("Error")){

                Text(
                    text = mensaje.split(":")[1],
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }else{
                Text(
                    text = mensaje,
                    textAlign = TextAlign.Center,
                    color = Color.Green
                )
            }
            // txtUser
            TextField(
                value = usuario,
                onValueChange = {
                    viewModel.updateUsuario(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Usuario")
                },
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            TextField(
                value = correo,
                onValueChange = {
                    viewModel.updateCorreo(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Correo")
                },
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            // txtPassword
            TextField(
                value = contrasenia,
                onValueChange = {
                    viewModel.updateContrasenia(it)
                },
                label = {
                    Text(text = "Contraseña")
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TextField(
                value = repcontrasenia,
                onValueChange = {
                    viewModel.updateRepContrasenia(it)
                },
                label = {
                    Text(text = "Repetir Contraseña")
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            // boton Ingresar
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                onClick = {
                    viewModel.crearUsuario(context as Activity)
                }
            ){
                Text("Crear")
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                thickness = 2.dp,
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp/*, start = 40.dp, end = 40.dp*/), // start -> izquierda, end -> derecha
                onClick = {
                    goToLoginScreen()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Gray400)
            ){
                Text("Volver".toUpperCase())
            }


            BackHandler {
                Log.d("LoginScreen", "XDDDDDDDDDDDDDDDDDDDDDDDdd")
                val activity = context as Activity
                activity.finish()
            }

        }
    }

}