package pe.edu.ulima.dbaccess.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pe.edu.ulima.dbaccess.models.beans.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(usuarios: List<Usuario>)

    @Query("DELETE FROM usuarios")
    fun deleteAllUsuarios()

    @Query("SELECT * FROM usuarios")
    fun getUsuarios(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE id = :usuarioId")
    fun getUsuarioById(usuarioId: Int): Usuario?

    @Query("SELECT * FROM usuarios WHERE user = :user AND password = :password")
    fun getUsuarioByUserAndPassword(user: String, password: Int): Usuario?

    @Update
    fun updateUsuario(usuario: Usuario)

    @Delete
    fun deleteUsuario(usuario: Usuario)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usuario: Usuario)

    @Query("SELECT name FROM usuarios WHERE user = :user")
    fun getUsuarioNombreByUser(user: String): String?

    @Query("SELECT COUNT(*) FROM usuarios")
    fun getUserCount(): Int

}

