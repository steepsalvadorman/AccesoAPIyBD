package pe.edu.ulima.dbaccess.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import pe.edu.ulima.dbaccess.navigations.AppNavigation
import pe.edu.ulima.dbaccess.ui.app.viewmodels.EditPerfilViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeFollowersScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeFollowsScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.PokemonDetailScreenViewModel
import pe.edu.ulima.dbaccess.ui.theme.AccesoDBTheme

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editPerfilViewModel = EditPerfilViewModel()
        val homeScreenViewModel = HomeScreenViewModel()
        val homeFollowsScreenViewModel = HomeFollowsScreenViewModel()
        val homeFollowersScreenViewModel = HomeFollowersScreenViewModel()
        val pokemonDetailScreenViewModel = PokemonDetailScreenViewModel()
        setContent {
            AccesoDBTheme{
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(
                        editPerfilViewModel= editPerfilViewModel,
                        homeScreenViewModel= homeScreenViewModel,
                        homeFollowsScreenViewModel = homeFollowsScreenViewModel,
                        homeFollowersScreenViewModel = homeFollowersScreenViewModel,
                        pokemonDetailScreenViewModel= pokemonDetailScreenViewModel
                    )
                }
            }
        }
    }
}

