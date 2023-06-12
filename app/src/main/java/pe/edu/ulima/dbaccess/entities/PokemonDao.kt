package pe.edu.ulima.dbaccess.entities

import androidx.room.*
import pe.edu.ulima.dbaccess.models.beans.Pokemon

@Dao
interface PokemonDao {
    @Insert
    fun insertMany(pokemon: List<Pokemon>)

    @Query("DELETE FROM pokemons")
    fun deleteAllPokemons()

    @Query("SELECT * FROM pokemons")
    fun getPokemons(): List<Pokemon>

    @Query("SELECT * FROM pokemons WHERE id = :pokemonId")
    fun getPokemonById(pokemonId: Int): Pokemon?

    @Update
    fun updatePokemon(pokemon: Pokemon)

    @Delete
    fun deletePokemon(pokemon: Pokemon)

    @Insert
    fun insertPokemon(pokemon: Pokemon)









}