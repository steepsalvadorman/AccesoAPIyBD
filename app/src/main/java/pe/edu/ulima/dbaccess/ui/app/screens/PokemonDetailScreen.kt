package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import pe.edu.ulima.dbaccess.R
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.ui.app.viewmodels.PokemonDetailScreenViewModel


@Preview
@Composable
fun PokemonDetailScreenPreview(){
    PokemonDetailScreen(
        PokemonDetailScreenViewModel(),
        id = 1,
        name = "steep",
        user = "nose",
        password = "nose",
        email = "nose@gmail.com",
        imageUrl = "nose"
    )
}



@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailScreenViewModel,
    id: Int,
    name: String,
    user: String,
    password: String,
    email: String,
    imageUrl: String,
){


    val userId = id
    val customColor = remember { mutableStateOf(Color(0xFFF37021)) }
    val seguidores: Int? by viewModel.seguidores.observeAsState(0)
    val seguidos: Int? by viewModel.seguidos.observeAsState(0)
    val pokemons: List<Pokemon>? by viewModel.pokemons.observeAsState()


    viewModel.setUsuario(userId)
    viewModel.setPokemons(userId.toString())
    viewModel.setImagen(userId.toString())
    viewModel.setSeguidores(userId.toString())
    viewModel.setSeguidos(userId.toString())






    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp), //Columna de la pokedex
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {

                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = "Imagen",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )


                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = user,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )


            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp), //Columna de Publicaciones
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,)
            {
                Text(text = pokemons?.size.toString(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                )

                Text(text = "Publicaciones",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp
                    )

                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp), //Columna de seguidos
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,)
            {
                Text(text = "${seguidos}",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    ),
                    modifier = Modifier
                        .clickable {
                            //seguidos
                            //goToFollowsScreen()
                            //navController.navigate("/pokemon/detalle?pokemon_id=${pokemon.id.toString()}")
                        }
                )

                Text(text = "Seguidos",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp), //Columna de seguidores
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,)
            {
                Text(text = "${seguidores}",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    ),
                    modifier = Modifier
                        .clickable {
                            //seguidores
                            //goToFollowersScreen()
                            //***************************
                            //navController.navigate("/pokemon/detalle?pokemon_id=${pokemon.id.toString()}")
                        }
                )

                Text(text = "Seguidores",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(), //Columna que ocupa todo el ancho
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    //goToEditPerfilScreen()
                }, //Primer boton Editar perfil
                modifier = Modifier
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = customColor.value
                )

            ) {
                Text(text = "Editar Perfil",
                    textAlign = TextAlign.Center,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )

            }
            Button(
                onClick = {}, //Segundo Boton Compartir Perfil
                modifier = Modifier
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = customColor.value
                )

            ) {
                Text(text = "Compartir Perfil",
                    textAlign = TextAlign.Center,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black)

            }

            Button(
                onClick = {}, // Tercer boton de imagen
                modifier = Modifier
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = customColor.value
                )
            ) {

                Box(
                    contentAlignment = Alignment.Center // Lo colocamos en un box para centrar
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Logo usuario",
                        modifier = Modifier
                            .size(25.dp),
                        colorFilter = ColorFilter.tint(
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )

                    )

                }

            }


        }

        Column {

            Text(text = "Historias Destacadas",
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "Guarda tus historias favoritas en el perfil",
                modifier = Modifier
                    .padding(start = 8.dp))

        }

        Row {
            Column( // Columna que va a contener al + y el texto Nueva
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(painter = painterResource(id = R.drawable.add),
                    contentDescription = "Logo Agregar",
                    modifier = Modifier
                        .size(80.dp),
                    colorFilter = ColorFilter.tint(
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )

                )
                Text(modifier = Modifier
                    .padding(top = 8.dp),
                    text = "Nueva")
            }
        }




        Column {
            if (pokemons.isNullOrEmpty()) {
                // Mostrar mensaje o indicador de carga
                Text(text = "No hay pokémones disponibles")
            } else {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(pokemons!!.size) { index ->
                        val pokemon = pokemons!![index]
                        Image(
                            painter = rememberImagePainter(data = pokemon.imageUrl),
                            contentDescription = pokemon.name,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 10.dp)
                        )
                    }
                }
            }
        }



    }
}