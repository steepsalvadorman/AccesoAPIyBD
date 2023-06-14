package pe.edu.ulima.dbaccess.configs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object BackendClient {
    //const val BASE_URL = "https://backendnavegacion.steepsalvador.repl.co/"
    const val BASE_URL ="http://192.168.0.108:8000/"


    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}
