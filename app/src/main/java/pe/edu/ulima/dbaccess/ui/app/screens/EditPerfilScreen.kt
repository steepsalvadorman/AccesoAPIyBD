package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import pe.edu.ulima.dbaccess.ui.app.viewmodels.EditPerfilViewModel
import pe.edu.ulima.dbaccess.ui.theme.Gray400
import pe.edu.ulima.dbaccess.ui.theme.Green200


@Preview
@Composable
fun EditPerfilScreenPreview(){
    EditPerfilScreen(

        EditPerfilViewModel(),
        goToHomeScreen = {}
    )
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun EditPerfilScreen(

    viewModel: EditPerfilViewModel,
    goToHomeScreen:() -> Unit,
){
    val context = LocalContext.current
    // viewmodel

    val nombre : String by viewModel.nombre.observeAsState(initial = "")
    val usuario : String by viewModel.usuario.observeAsState(initial = "")
    val correo : String by viewModel.correo.observeAsState(initial = "")
    val mensaje : String by viewModel.mensaje.observeAsState(initial = "")


    // Editar Perfil

    val activity = context as Activity
    val intent = activity.intent
    val userId = intent.getIntExtra("user_id", 0)
    val contraseñaantigua : String by viewModel.acontraseña.observeAsState(initial = "")
    val contraseñanueva : String by viewModel.ncontraseña.observeAsState(initial = "")
    val contraseñarepetida : String by viewModel.rcontraseña.observeAsState(initial = "")
    val mensaje2 : String by viewModel.mensaje2.observeAsState(initial = "")
    val imagenEditPerfil: String? by viewModel.imagen.observeAsState("")

    viewModel.setImagen(userId.toString())


    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                val activity = context as Activity
                activity.finish()
            },
            modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)
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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Editar Perfil",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 40.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = rememberImagePainter(data = imagenEditPerfil),
                    contentDescription = "Logo Pokedex",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )


            }
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
                value = nombre,
                onValueChange = {
                    viewModel.updateNombre(it)
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp),
                label = {
                    Text(text = "Nombre")
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
                value = usuario,
                onValueChange = {
                    viewModel.updateUsuario(it)
                },
                label = {
                    Text(text = "Usuario")
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp),
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            TextField(
                value = correo,
                onValueChange = {
                    viewModel.updateCorreo(it)
                },
                label = {
                    Text(text = "Correo")
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp),
                placeholder = {
                    Text(text= "")
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, start = 40.dp, end = 40.dp),
                onClick = {
                    viewModel.validaEditarPerfil(userId = userId.toString(), context)
                },
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)) ,
                colors = ButtonDefaults.buttonColors(backgroundColor = Green200)
            ){
                Text(
                    "ACTUALIZAR DATOS",
                    color = Color.White
                )
            }
            var showModal by remember { mutableStateOf(false) }

            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp),
                onClick = {
                    showModal = !showModal
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Gray400)
            ){
                Text("Cambiar contraseña".toUpperCase())
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp), // start -> izquierda, end -> derecha
                onClick = {
                    goToHomeScreen()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Gray400)
            ){
                Text("Volver".toUpperCase())
            }







            if (showModal) {
                val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
                ModalBottomSheetLayout(
                    sheetState = bottomSheetState,
                    sheetContent = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .background(Color.White)
                                .padding(start = 30.dp, end = 30.dp)
                        ) {
                            // Contenido del modal aquí

                            Column {
                                TextField(
                                    value = contraseñaantigua,
                                    onValueChange = {
                                        viewModel.updateAcontraseña(it)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(text = "Contraseña Antigua")
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
                                    value = contraseñanueva,
                                    onValueChange = {
                                        viewModel.updateNcontraseña(it)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(text = "Contraseña Nueva")
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
                                    value = contraseñarepetida,
                                    onValueChange = {
                                        viewModel.updateRcontraseña(it)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(text = "Contraseña Repetida")
                                    },
                                    placeholder = {
                                        Text(text= "")
                                    },
                                    singleLine = true,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent
                                    )
                                )

                                if(mensaje2.contains("Error")){

                                    Text(
                                        text = mensaje2.split(":")[1],
                                        textAlign = TextAlign.Center,
                                        color = Color.Red
                                    )
                                }else{
                                    Text(
                                        text = mensaje2,
                                        textAlign = TextAlign.Center,
                                        color = Color.Green
                                    )
                                }
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        viewModel.validacionActualizarContraseña(userId = userId.toString(), context)

                                    }
                                ) {
                                    Text("ACTUALIZAR CONTRASEÑA")
                                }
                            }

                        }
                    },
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    scrimColor = Color(0xFF000000).copy(alpha = 0f),
                    content = {}
                )
            }

        }
    }
    BackHandler {
        val activity = context as Activity
        activity.finish()
    }

}