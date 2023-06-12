package pe.edu.ulima.dbaccess.models.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemons")
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var number: Int = 0,
    var name: String = "",
    var weight: Float = 0F,
    var height: Float = 0F,
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",
    @SerializedName("generation_id")
    @ColumnInfo(name = "generation_id")
    var generationId: Int,
)