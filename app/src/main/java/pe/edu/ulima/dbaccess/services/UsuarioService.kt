package pe.edu.ulima.dbaccess.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface UsuarioService {






    @POST("/user/create")
    fun createUser(
        @Body user: Usuario
    ): Call<String>

    @POST("/user/reset_password")
    fun resetPassword(
        @Body email: String
    ): Call<String>

    @POST("/user/validate")
    fun validateUser(
        @Body credentials: Usuario
    ): Call<Usuario>

    @GET("/user/fetch_one")
    fun fetchUser(
        @Query("id") userId: String
    ): Call<Usuario>

    @GET("/user/pokemon")
    fun getUserPokemons(
        @Query("id") userId: String
    ): Call<List<Pokemon>>

    @GET("/user/following")
    fun getUserFollowing(
        @Query("user_id") userId: String
    ): Call<List<Usuario>>

    @GET("/user/follower")
    fun getUserFollowers(
        @Query("user_id") userId: String
    ): Call<List<Usuario>>

    @Multipart
    @POST("/upload/demo")
    fun uploadDemo(
        @Part("extra_data") extraData: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<String>

    @POST("/user/update")
    fun updateUser(
        @Body updateUserRequest: Usuario
    ): Call<String>

    @POST("/user/password")
    fun updatePassword(
        @Body updatePasswordRequest: Usuario
    ): Call<String>

    @GET("/user/search_id")
    fun getIdFromUserAndPass(
        @Query("user") user: String,
        @Query("password") password: String
    ): Call<String>


    @Multipart
    @POST("/user/pass_recovery")
    fun recoverPassword(
        @Part("email") email: RequestBody
    ): Call<Usuario>



    @POST("/user/password_search")
    fun userPasswordSearch(
        @Body usuario: Usuario
    ): Call<String>






}


