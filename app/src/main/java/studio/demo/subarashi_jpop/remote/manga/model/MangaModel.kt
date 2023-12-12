package studio.demo.subarashi_jpop.remote.manga.model

data class MangaModel(
    val mal_id: Int,
    val url: String,
    val images: String,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    val title_english: String?,
    val title_japanese: String?,
    val type: String,
    val chapters: Int?,
    val volumes: Int?,
    val status: String,
    val publishing: Boolean,
    val published: Published,
    val score: Float?,
    val scored_by: Int?,
    val rank: Int?,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String?,
    val background: String?,
    val authors: List<Author>,
    val serializations: List<Serialization>,
    val genres: List<MangaGenre>,
    val explicit_genres: List<MangaGenre>,
    val themes: List<Theme>,
    val demographics: List<MangaDemographic>
)