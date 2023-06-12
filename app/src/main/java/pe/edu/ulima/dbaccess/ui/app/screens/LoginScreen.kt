package pe.edu.ulima.dbaccess.ui.app.screens


import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import pe.edu.ulima.dbaccess.R
import pe.edu.ulima.dbaccess.ui.app.viewmodels.LoginScreenViewModel
import pe.edu.ulima.dbaccess.ui.theme.Gray400
import pe.edu.ulima.dbaccess.ui.theme.Green200


@Preview
@Composable
fun HomeScreenLoginPreview(){
    LoginScreen(
        LoginScreenViewModel(),
        goToResetPasswordScreen = {},
        goToCreateAccountScreen = {},

    )
}

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    goToCreateAccountScreen:() -> Unit,
    goToResetPasswordScreen: () -> Unit,

    ){
    val context = LocalContext.current
    // viewmodel
    val usuario : String by viewModel.usuario.observeAsState( "")
    val contrasenia : String by viewModel.contrasenia.observeAsState( "")
    val mensaje : String by viewModel.mensaje.observeAsState( "")
    // close
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                Log.d("LOGIN_SCREEN", "XDDDDDDDDDDDDDDDDDDD")
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
                painter = rememberImagePainter(
                    data = "https://www.kindpng.com/picc/m/541-5418191_original-pokedex-hd-png-download.png"
                ),
                contentDescription = "Logo Ulima",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 10.dp),
            )

            Text(
                text = "Bienvenido",
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
            // txtPassword
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

            // boton Ingresar
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                onClick = {
                    viewModel.validarUsuario(context, usuario, contrasenia)
                    // Realiza la navegación a la ruta composable "/login/home" después de validar el usuario
                }
            ) {
                Text("INGRESAR")
            }

            // boton Ingresar con Google
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, /*start = 40.dp, end = 40.dp*/),
                onClick = {

                },
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)) ,
                colors = ButtonDefaults.buttonColors(backgroundColor = Green200)
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Logo Google",
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 10.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    "INGRESAR CON GOOGLE",
                    color = Color.White
                )
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
                    goToCreateAccountScreen()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Gray400)
            ){
                Text("Crear Cuenta".toUpperCase())
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    goToResetPasswordScreen()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Gray400)
            ){
                Text("Recuperar contraseña".toUpperCase())
            }

            BackHandler {
                Log.d("LoginScreen", "XDDDDDDDDDDDDDDDDDDDDDDDdd")
                val activity = context as Activity
                activity.finish()
            }

        }
    }
}