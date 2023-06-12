package pe.edu.ulima.dbaccess.services

import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService{
    @GET("/pokemon/list")
    fun fetchAll(
        @Query("name") name: String,
        @Query("generation_ids") generationIds: String
    ): Call<PokemonList>

    @GET("/pokemon/{id}")
    fun fetchOne(
        @Path("id") id: Int
    ): Call<Pokemon>

}