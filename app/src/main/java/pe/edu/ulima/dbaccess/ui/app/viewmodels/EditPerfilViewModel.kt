package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.models.beans.Usuario
import pe.edu.ulima.dbaccess.services.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class EditPerfilViewModel : ViewModel() {


    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    fun updateId(it: Int) {
        _id.value = it
    }

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> get() = _nombre

    fun updateNombre(it: String) {
        _nombre.value = it
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
            }

        }.start()
    }



    private val _usuario = MutableLiveData<String>("")
    val usuario: LiveData<String> get() = _usuario

    fun updateUsuario(it: String) {
        _usuario.value = it
    }

    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> = _correo

    fun updateCorreo(it: String) {
        _correo.value = it
    }

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    fun updateMensaje(it: String) {
        _mensaje.postValue(it)
    }


    private val _mensaje2 = MutableLiveData<String>()
    val mensaje2: LiveData<String> = _mensaje2

    fun updateMensaje2(it: String) {
        _mensaje2.value = it
    }


    private val _acontraseña = MutableLiveData<String>()
    val acontraseña: LiveData<String> = _acontraseña

    fun updateAcontraseña(it: String) {
        _acontraseña.value = it
    }

    private val _ncontraseña = MutableLiveData<String>()
    val ncontraseña: LiveData<String> = _ncontraseña

    fun updateNcontraseña(it: String) {
        _ncontraseña.value = it
    }

    private val _rcontraseña = MutableLiveData<String>()
    val rcontraseña: LiveData<String> = _rcontraseña

    fun updateRcontraseña(it: String) {
        _rcontraseña.value = it
    }

    fun validacionActualizarContraseña(userId: String, activity: Activity) {
        if (_acontraseña.value.isNullOrBlank()) {
            updateMensaje2("Error: Por favor ingrese la contraseña antigua.")
            return
        }

        if (_ncontraseña.value.isNullOrBlank()) {
            updateMensaje2("Error: Por favor ingrese la contraseña nueva.")
            return
        }

        if (_rcontraseña.value.isNullOrBlank()) {
            updateMensaje2("Error: Por favor ingrese la contraseña repetida.")
            return
        }

        if (_ncontraseña.value != _rcontraseña.value) {
            updateMensaje2("Error: Las contraseñas deben ser iguales")
            return
        }

        val antiguaContraseña = _acontraseña.value

        isValidContraseñaAntigua(userId, activity, antiguaContraseña!!)
    }

    fun isValidContraseñaAntigua(userId: String, activity: Activity, antiguaContraseña: String) {
        val nuevoUsuario = Usuario(
            id = userId.toInt(),
            password = antiguaContraseña
        )

        val apiService = BackendClient.buildService(UsuarioService::class.java)
        apiService.userPasswordSearch(nuevoUsuario).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                activity.runOnUiThread {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (!responseBody.isNullOrEmpty()) {
                            actualizarContraseña(userId, activity)
                        } else {
                            updateMensaje2("Error: La contraseña antigua no es correcta")
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Error: Ingrese los datos correctamente"
                        updateMensaje2("Error: $errorMessage")
                        Log.d("actualizarContraseña1", errorMessage)
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val errorMessage = "Error: ${t.message}"
                updateMensaje2(errorMessage)
                Log.d("actualizarContraseña1", errorMessage)
            }
        })
    }

    fun actualizarContraseña(userId: String, activity: Activity) {
        val contraseñaN = _ncontraseña.value

        val nuevoUsuario = Usuario(
            id = userId.toInt(),
            password = contraseñaN!!
        )

        val apiService = BackendClient.buildService(UsuarioService::class.java)
        apiService.updatePassword(nuevoUsuario).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                activity.runOnUiThread {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            updateMensaje2(responseBody)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Error: Ingrese los datos correctamente"
                        updateMensaje2("Error: $errorMessage")
                        Log.d("actualizarContraseña1", errorMessage)
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val errorMessage = "Error: ${t.message}"
                updateMensaje2(errorMessage)
                Log.d("actualizarContraseña2", errorMessage)
            }
        })
    }









    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(email)
    }

    fun validaEditarPerfil(userId: String, activity: Activity) {
        val nombre = _nombre.value
        val correo = _correo.value
        val usuario = _usuario.value

        if (nombre.isNullOrEmpty()) {
            updateMensaje("Error: El nombre es obligatorio")
        } else if (correo.isNullOrEmpty() || !isValidEmail(correo)) {
            updateMensaje("Error: El correo electrónico no es válido")
        } else if (usuario.isNullOrEmpty()) {
            updateMensaje("Error: El usuario es obligatorio")
        } else {
            actualizarPerfil(userId, activity)
        }



    }




    fun actualizarPerfil(userId: String, activity: Activity) {
        val nombre = _nombre.value
        val usuario = _usuario.value
        val correo = _correo.value

        Thread {
            try {
                val nuevoUsuario = Usuario(
                    id = userId.toInt(),
                    user = usuario!!,
                    email = correo!!,
                    name = nombre!!
                )

                val apiService = BackendClient.buildService(UsuarioService::class.java)
                val response = apiService.updateUser(nuevoUsuario).execute()

                activity.runOnUiThread {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            updateMensaje(responseBody)
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Error: Ingrese los datos correctamente"
                        updateMensaje("Error: $errorMessage")
                        Log.d("ActualizarPerfil", errorMessage)
                    }
                }
            } catch (e: Exception) {
                // Manejar excepción
                val errorMessage = "Error: ${e.message}"
                updateMensaje(errorMessage)
                Log.d("ActualizarPerfil", errorMessage)
            }
        }.start()
    }













}