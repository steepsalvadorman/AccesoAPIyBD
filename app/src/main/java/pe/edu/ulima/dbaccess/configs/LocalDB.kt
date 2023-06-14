package pe.edu.ulima.dbaccess.configs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.ulima.dbaccess.entities.PokemonDao
import pe.edu.ulima.dbaccess.entities.ProfileKeyDao
import pe.edu.ulima.dbaccess.entities.UsuarioDao
import pe.edu.ulima.dbaccess.models.beans.Pokemon
import pe.edu.ulima.dbaccess.models.beans.ProfileKey
import pe.edu.ulima.dbaccess.models.beans.Usuario

@Database(
    entities =[Pokemon::class, ProfileKey::class, Usuario::class],
    version = 1
)
abstract class LocalDB : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun profileKeyDao(): ProfileKeyDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        private var INSTANCE: LocalDB? = null

        fun getDatabase(context: Context): LocalDB {
            if (INSTANCE == null) {
                synchronized(LocalDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDB::class.java,
                        "local_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}