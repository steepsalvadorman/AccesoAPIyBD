package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.models.beans.Usuario
import pe.edu.ulima.dbaccess.services.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ResetPasswordScreenViewModel : ViewModel() {


    private val _correo = MutableLiveData<String>("")
    var correo: LiveData<String> = _correo
    fun updateCorreo(it: String){
        _correo.postValue(it)
    }

    private val _mensaje = MutableLiveData<String>("")
    var mensaje: LiveData<String> = _mensaje
    fun updateMensaje(it: String){
        _mensaje.postValue(it)
    }



    fun validateEmail() {
        val emailRegex = "^[\\w\\.-]+@[\\w\\.-]+\\.[\\w-]{2,}$".toRegex()

        // Verificar si el campo de correo está lleno
        if (correo.value.isNullOrBlank()) {
            updateMensaje("Error: Por favor ingrese un correo electrónico.")
            return
        }

        // Verificar si el correo cumple con el formato de un correo electrónico
        if (!correo.value!!.matches(emailRegex)) {
            updateMensaje("Error: El correo electrónico ingresado no es válido.")
            return
        }

        recoverPassword()

    }



    fun recoverPassword() {
        Thread {
            try {
                val apiService = BackendClient.buildService(UsuarioService::class.java)
                val emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), correo.value.toString())
                val response = apiService.recoverPassword(emailRequestBody).execute()

                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        val mensaje = "Contraseña Recuperada: ${usuario.password}"
                        updateMensaje(mensaje)
                    } else {
                        updateMensaje("Error: No se pudo recuperar la contraseña")
                    }
                } else {
                    updateMensaje("Error: No tiene contraseña")
                }

            } catch (e: Exception) {
                val errorMessage = "Error: ${e.message}"
                updateMensaje(errorMessage)
                Log.d("ResetPasswordScreen", errorMessage)
            }
        }.start()
    }














}



