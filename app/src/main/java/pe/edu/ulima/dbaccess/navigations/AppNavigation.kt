package pe.edu.ulima.dbaccess.navigations

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.ulima.dbaccess.models.beans.Usuario
import pe.edu.ulima.dbaccess.ui.app.screens.EditPerfilScreen
import pe.edu.ulima.dbaccess.ui.app.screens.HomeFollowersScreen
import pe.edu.ulima.dbaccess.ui.app.screens.HomeFollowsScreen
import pe.edu.ulima.dbaccess.ui.app.screens.HomeScreen
import pe.edu.ulima.dbaccess.ui.app.screens.PokemonDetailScreen
import pe.edu.ulima.dbaccess.ui.app.viewmodels.EditPerfilViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeFollowersScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeFollowsScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.PokemonDetailScreenViewModel



@Composable
fun AppNavigation(
    editPerfilViewModel: EditPerfilViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    homeFollowsScreenViewModel: HomeFollowsScreenViewModel,
    homeFollowersScreenViewModel: HomeFollowersScreenViewModel,
    pokemonDetailScreenViewModel: PokemonDetailScreenViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable(route = "home") {
            HomeScreen(
                goToEditPerfilScreen = {
                    navController.navigate("home/edit_perfil")
                },
                goToFollowsScreen = {
                    navController.navigate("home/follows")
                },
                goToFollowersScreen = {
                    navController.navigate("home/followers")
                },
                homeScreenViewModel
            )
        }

        composable(route = "home/edit_perfil") {
            EditPerfilScreen(
                editPerfilViewModel,
                goToHomeScreen = {
                    navController.navigate("home")
                }
            )
        }

        composable(route = "home/followers") {
            HomeFollowersScreen(
                homeFollowersScreenViewModel,
                goToPokemonDetailScreen = { usuario ->
                    if (usuario != null) {
                        homeFollowersScreenViewModel.selectedUsuario = usuario
                        navController.navigate("home/followers/detail")
                    }
                }
            )
        }

        composable(route = "home/followers/detail") {
            val usuario = homeFollowersScreenViewModel.selectedUsuario ?: Usuario(0, "", "", "", "", "")

            PokemonDetailScreen(
                pokemonDetailScreenViewModel,
                usuario.id,
                usuario.name,
                usuario.user,
                usuario.password,
                usuario.email,
                usuario.imageUrl
            )
        }








        composable(route = "home/follows") {
            HomeFollowsScreen(
                homeFollowsScreenViewModel,
                goToPokemonDetailScreen = { usuario ->
                    if (usuario != null) {
                        homeFollowsScreenViewModel.selectedUsuario = usuario
                        navController.navigate("home/follows/detail")
                    }
                }
            )
        }

        composable(route = "home/follows/detail") {
            val usuario = homeFollowsScreenViewModel.selectedUsuario ?: Usuario(0, "", "", "", "", "")

            PokemonDetailScreen(
                pokemonDetailScreenViewModel,
                usuario.id,
                usuario.name,
                usuario.user,
                usuario.password,
                usuario.email,
                usuario.imageUrl
            )
        }








    }
}




