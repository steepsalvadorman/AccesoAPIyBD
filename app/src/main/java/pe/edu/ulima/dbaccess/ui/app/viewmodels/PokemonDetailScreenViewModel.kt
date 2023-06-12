package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.services.UsuarioService


class PokemonDetailScreenViewModel: ViewModel() {

    private val _usuario = MutableLiveData<String>("")
    val usuario: LiveData<String> = _usuario

    fun setUsuario(userId: Int) {
        Thread {
            val apiService = BackendClient.buildService(UsuarioService::class.java)
            val response = apiService.fetchUser(userId.toString()).execute()

            if (response.isSuccessful) {
                val usuario = response.body()
                Handler(Looper.getMainLooper()).post {
                    _usuario.value = usuario?.user
                }
            }
        }.start()
    }



    private val _pokemonsLiveData = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> get() = _pokemonsLiveData

    fun setPokemons(userId: String) {


        Thread {
            val apiService = BackendClient.buildService(UsuarioService::class.java)
            val response = apiService.getUserPokemons(userId = userId).execute()
            if (response.isSuccessful) {
                val pokemonList = response.body()
                if (pokemonList != null) {
                    _pokemonsLiveData.postValue(pokemonList!!)
                }
            } else {
                // Manejar error de respuesta
                // Puedes mostrar un mensaje de error o realizar alguna acción adicional
            }
        }.start()
    }











    private val _imagen = MutableLiveData<String?>()
    val imagen: LiveData<String?> = _imagen

    fun setImagen(userId: String) {
        Thread {

            val apiService = BackendClient.buildService(UsuarioService::class.java)
            val response = apiService.fetchUser(userId = userId).execute()
            if (response.isSuccessful) {
                val imagen = response.body()?.imageUrl
                Handler(Looper.getMainLooper()).post {
                    _imagen.value = imagen
                }
            } else {
                // Manejar error de respuesta
                // Puedes mostrar un mensaje de error o realizar alguna acción adicional
            }

        }.start()
    }








    private val _seguidores = MutableLiveData<Int?>(0)
    val seguidores: LiveData<Int?> get() = _seguidores


    fun setSeguidores(userId: String) {

        Thread {

            val apiService = BackendClient.buildService(UsuarioService::class.java)
            val response = apiService.getUserFollowing(userId = userId).execute()
            if (response.isSuccessful) {
                val cantidad = response.body()!!.size
                Handler(Looper.getMainLooper()).post {
                    _seguidores.value = cantidad
                }
            } else {
                // Manejar error de respuesta
                // Puedes mostrar un mensaje de error o realizar alguna acción adicional
            }

        }.start()

    }


    private val _seguidos = MutableLiveData<Int?>(0)
    val seguidos: LiveData<Int?> = _seguidos

    fun setSeguidos(userId: String) {
        Thread {

            val apiService = BackendClient.buildService(UsuarioService::class.java)
            val response = apiService.getUserFollowers(userId = userId).execute()
            if (response.isSuccessful) {
                val cantidad = response.body()!!.size
                Handler(Looper.getMainLooper()).post {
                    _seguidos.value = cantidad
                }
            } else {
                // Manejar error de respuesta
                // Puedes mostrar un mensaje de error o realizar alguna acción adicional
            }

        }.start()
    }
}