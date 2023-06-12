package pe.edu.ulima.dbaccess.models.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    var name: String = "",
    var user: String = "",
    var password: String = "",
    var email: String = "",
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    var imageUrl: String = ""
)


