package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.configs.LocalDB
import pe.edu.ulima.dbaccess.models.beans.Usuario
import pe.edu.ulima.dbaccess.services.UsuarioService
import kotlin.concurrent.thread

class CreateAccountScreenViewModel : ViewModel() {
    private val _usuario = MutableLiveData<String>("")
    var usuario: LiveData<String> = _usuario

    private val _correo = MutableLiveData<String>("")
    var correo: LiveData<String> = _correo

    private val _contrasenia = MutableLiveData<String>()
    val contrasenia: LiveData<String> = _contrasenia

    private val _repcontrasenia = MutableLiveData<String>()
    val repcontrasenia: LiveData<String> = _repcontrasenia

    fun updateContrasenia(it: String) {
        _contrasenia.value = it
    }

    fun updateRepContrasenia(it: String) {
        _repcontrasenia.value = it
    }


    private val _mensaje = MutableLiveData<String>("")
    var mensaje: LiveData<String> = _mensaje

    fun updateUsuario(it: String) {
        _usuario.postValue(it)
    }

    fun updateCorreo(it: String) {
        _correo.postValue(it)
    }


    fun updateMensaje(it: String){
        _mensaje.postValue(it)
    }





    fun crearUsuario(activity: Activity) {
        val apiService = BackendClient.buildService(UsuarioService::class.java)

        // Obtener los valores ingresados por el usuario
        val usuario = _usuario.value
        val correo = _correo.value
        val contrasenia = _contrasenia.value
        val repContrasenia = _repcontrasenia.value


        val emailRegex = "^[\\w\\.-]+@[\\w\\.-]+\\.[\\w-]{2,}$".toRegex()


        // Verificar si el campo de correo está lleno
        if (correo.isNullOrBlank()) {
            updateMensaje("Error: Por favor ingrese un correo electrónico.")
            return
        }

        if (!correo.matches(emailRegex)) {
            updateMensaje("Error: El correo electrónico ingresado no es válido.")
            return
        }



        // Validar que los campos no estén vacíos
        if (usuario.isNullOrEmpty() || correo.isNullOrEmpty() || contrasenia == null || repContrasenia == null) {
            activity.runOnUiThread {
                updateMensaje("Error: Todos los campos son requeridos")
            }
            return
        }

        // Validar que las contraseñas coincidan
        if (contrasenia != repContrasenia) {
            activity.runOnUiThread {
                updateMensaje("Error: Las contraseñas no coinciden")
            }
            return
        }

        thread {
            try {
                // Crear el objeto de usuario con los valores ingresados
                val nuevoUsuario = Usuario(
                    name = usuario,
                    user = usuario,
                    password = contrasenia,
                    email = correo,
                    imageUrl = ""
                )

                // Insertar el nuevo usuario en el backend
                val response = apiService.createUser(nuevoUsuario).execute()

                if (response.isSuccessful) {
                    // Usuario creado en el backend, guardar en la base de datos local
                        activity.runOnUiThread {
                            updateMensaje("Usuario creado exitosamente en el Backend")
                        }
                    } else {
                        activity.runOnUiThread {
                            updateMensaje("Error: No se pudo guardar el usuario en el Backend")
                        }
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                activity.runOnUiThread {
                    updateMensaje("Error: No se pudo conectar al Backend")
                }
            }
        }
    }








}