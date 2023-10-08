package studio.demo.subarashi_jpop.topanime


import com.google.gson.annotations.SerializedName

data class Theme(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)