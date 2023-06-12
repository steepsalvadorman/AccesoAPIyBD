package pe.edu.ulima.dbaccess.ui.app.viewmodels

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.ulima.dbaccess.activities.AppActivity
import pe.edu.ulima.dbaccess.configs.BackendClient
import pe.edu.ulima.dbaccess.models.beans.Usuario
import pe.edu.ulima.dbaccess.services.UsuarioService


class LoginScreenViewModel : ViewModel() {
    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    fun updateUsuario(it: String) {
        _usuario.value = it
    }

    private val _contrasenia = MutableLiveData<String>()
    val contrasenia: LiveData<String> = _contrasenia

    fun updateContrasenia(it: String) {
        _contrasenia.value = it
    }

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    fun updateMensaje(it: String) {
        _mensaje.value = it

        // Mostrar el mensaje durante 3 segundos y luego borrarlo
        val handler = Handler()
        handler.postDelayed({
            _mensaje.value = ""
        }, 3000) // 3000 milisegundos = 3 segundos
    }



    fun validarUsuario(context: Context, usuario: String, password: String) {
        val apiService = BackendClient.buildService(UsuarioService::class.java)

        Thread {
            val response = apiService.validateUser(Usuario(user = usuario, password = password)).execute()

            if (response.isSuccessful) {
                Handler(Looper.getMainLooper()).post {
                    updateMensaje("El usuario se encuentra en el backend")
                }

                val id: Int? = response.body()?.id

                Handler(Looper.getMainLooper()).post {
                    updateMensaje("Todo OK")
                    val appActivity = Intent(context, AppActivity::class.java)
                    appActivity.putExtra("user_id", id)
                    context.startActivity(appActivity)
                    // Si deseas cerrar la actividad actual después de iniciar la siguiente actividad, puedes llamar a finish()
                    // (context as Activity).finish()
                }
            } else {
                // Error en la llamada al servidor
                Handler(Looper.getMainLooper()).post {
                    updateMensaje("Error: El usuario y/o contraseña estan mal")
                }
            }
        }.start()


    }


    fun obtenerIdUsuarioLogeado(context: Context, usuario: String, password: String): Int {
        val apiService = BackendClient.buildService(UsuarioService::class.java)

        try {
            val response = apiService.getIdFromUserAndPass(user = usuario, password = password).execute()
            if (response.isSuccessful) {
                return response.body()?.toInt() ?: 0
            }
        } catch (e: Exception) {
            // Imprimir el mensaje de error
            e.printStackTrace()
        }

        return 0 // Valor predeterminado si no se puede obtener el ID del usuario logeado
    }





















}
