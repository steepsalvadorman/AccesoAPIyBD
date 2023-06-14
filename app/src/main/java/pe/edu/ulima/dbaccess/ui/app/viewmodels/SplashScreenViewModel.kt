package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.dbaccess.activities.AppActivity
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.ProfileKey
import pe.edu.ulima.dbaccess.services.PokemonService
import kotlin.concurrent.thread

class SplashScreenViewModel: ViewModel() {
    private val _pokemons = MutableLiveData<List<Pokemon>?>()
    val pokemons: LiveData<List<Pokemon>?> get() = _pokemons

    fun setPokemons(activity: Activity) {
        val apiService = BackendClient.buildService(PokemonService::class.java)
        thread {
            try {
                // db
                val userId = 1
                val database = LocalDB.getDatabase(activity as Context)
                val profileKeyDao = database.profileKeyDao()
                val profileKey: ProfileKey? = profileKeyDao.getProfileUserById(userId)
                Log.d("HOME_VIEW_MODEL", "0 ++++++++++++++++++++++++++++++")
                Log.d("HOME_VIEW_MODEL", profileKey.toString())

                if (profileKey == null) {
                    Log.d("HOME_VIEW_MODEL", "1 +++++++++++++++++++++++++++++++ IF")
                    val response = apiService.fetchAll("", "").execute()
                    val pokemonDao = database.pokemonDao()
                    if (response.isSuccessful) {
                        val pokemonList = response.body()
                        Handler(Looper.getMainLooper()).post {
                            _pokemons.value = pokemonList
                        }
                        pokemonList?.let {
                            pokemonDao.deleteAllPokemons()
                            pokemonDao.insertMany(it)
                        }
                        profileKeyDao.setFirstLoad(ProfileKey(userId = userId, firstLoad = true))
                    }
                } else {
                    val pokemonDao = database.pokemonDao()
                    Handler(Looper.getMainLooper()).post {
                        _pokemons.value = pokemonDao.getPokemons()
                    }
                    Log.d("HOME_VIEW_MODEL", "2 +++++++++++++++++++++++++++++++ ELSE")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        activity,
                        "Error: No se pudo obtener la lista de Pokémon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun checkUser(context: Context, navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val database = LocalDB.getDatabase(context)
                val userDao = database.usuarioDao()
                val userCount = userDao.getUserCount()
                val user = userDao.getUsuarios()
                val idUser = user[0].id

                withContext(Dispatchers.Main) {
                    if (userCount != 0) {
                        // hay un usuario en db
                        val appActivity = Intent(context, AppActivity::class.java)
                        appActivity.putExtra("user_id", idUser)
                        context.startActivity(appActivity)
                        withContext(Dispatchers.Main) {
                            navController.navigate("home")
                        }
                    } else {
                        // no hay un usuario en db
                        withContext(Dispatchers.Main) {
                            navController.navigate("/login")
                        }
                    }
                }

            } catch (e: Exception) {
                // Manejar excepción y mostrar error en el Logcat
                val errorMessage = "Error: No se pudo acceder a la base de datos"
                Log.e("CheckUser", errorMessage, e)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }


        }
    }


}