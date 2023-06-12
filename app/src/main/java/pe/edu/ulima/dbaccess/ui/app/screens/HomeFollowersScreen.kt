package pe.edu.ulima.dbaccess.ui.app.screens

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import pe.edu.ulima.dbaccess.models.beans.Usuario
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeFollowersScreenViewModel

@Preview
@Composable
fun HomeFollowersScreenPreview(){
    HomeFollowersScreen(
        HomeFollowersScreenViewModel(),
        goToPokemonDetailScreen = {}
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun HomeFollowersScreen(
    viewModel: HomeFollowersScreenViewModel,
    goToPokemonDetailScreen: (Usuario?) -> Unit,
) {
    val context = LocalContext.current
    val activity = context as Activity
    val intent = activity.intent
    val userId = intent.getIntExtra("user_id", 0)
    val usuario: String by viewModel.usuario.observeAsState("")
    val seguidores: Int? by viewModel.seguidores.observeAsState(0)
    val seguidos: Int? by viewModel.seguidos.observeAsState(0)
    val pokemonsfollowers: List<Usuario>? by viewModel.pokemons.observeAsState()
    val pokemonsfollows: List<Usuario>? by viewModel.pokemonsF.observeAsState()

    viewModel.setFollowersPokemons(userId.toString())
    viewModel.setFollowsPokemons(userId.toString())
    viewModel.setUsuario(userId)
    viewModel.setSeguidores(userId.toString())
    viewModel.setSeguidos(userId.toString())

    val selectedTabIndex = remember { mutableStateOf(0) }

    Column {
        Text(
            text = "Usuario: $usuario", // Mostrar el nombre del usuario logeado
            modifier = Modifier.padding(16.dp)
        )
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = isTabSelected(0, selectedTabIndex.value),
                onClick = {
                    selectedTabIndex.value = 0
                },
                text = { Text("Seguidores: $seguidores") }
            )
            Tab(
                selected = isTabSelected(1, selectedTabIndex.value),
                onClick = {
                    selectedTabIndex.value = 1
                },
                text = { Text("Seguidos: $seguidos") }
            )
        }

        if (selectedTabIndex.value == 0) {
            if (pokemonsfollowers.isNullOrEmpty()) {
                // Mostrar mensaje o indicador de carga
                Text(text = "No hay seguidores disponibles")
            } else {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(pokemonsfollowers!!.size) { index ->
                        val pokemon = pokemonsfollowers!![index]
                        Image(
                            painter = rememberImagePainter(data = pokemon.imageUrl),
                            contentDescription = pokemon.name,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 10.dp)
                                .clickable {
                                    goToPokemonDetailScreen(pokemon)
                                }
                        )
                    }
                }
            }
        } else if (selectedTabIndex.value == 1) {
            // Mostrar el LazyVerticalGrid de seguidos
            // ...
            if (pokemonsfollows.isNullOrEmpty()) {
                // Mostrar mensaje o indicador de carga
                Text(text = "No hay seguidos disponibles")
            } else {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(pokemonsfollows!!.size) { index ->
                        val pokemon = pokemonsfollows!![index]
                        Image(
                            painter = rememberImagePainter(data = pokemon.imageUrl),
                            contentDescription = pokemon.name,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 10.dp)
                                .clickable {
                                goToPokemonDetailScreen(pokemon)

                                }
                        )
                    }
                }
            }


        }
    }
}

private fun isTabSelected(index: Int, selectedTabIndex: Int): Boolean {
    return index == selectedTabIndex
}
