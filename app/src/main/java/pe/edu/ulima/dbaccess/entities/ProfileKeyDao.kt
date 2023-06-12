package pe.edu.ulima.dbaccess.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.dbaccess.models.beans.ProfileKey

@Dao
interface ProfileKeyDao {
    @Insert
    @Override
    fun setFirstLoad(profileKey: ProfileKey)

    @Override
    @Query("SELECT * FROM profile_keys WHERE user_id = :userId")
    fun getProfileUserById(userId: Int): ProfileKey?

    @Query("DELETE FROM profile_keys")
    fun deleteAllProfileKeys()

}