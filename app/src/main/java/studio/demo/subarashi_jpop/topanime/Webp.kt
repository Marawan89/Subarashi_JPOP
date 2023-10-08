package studio.demo.subarashi_jpop.topanime


import com.google.gson.annotations.SerializedName

data class Webp(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("large_image_url")
    val largeImageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String
)