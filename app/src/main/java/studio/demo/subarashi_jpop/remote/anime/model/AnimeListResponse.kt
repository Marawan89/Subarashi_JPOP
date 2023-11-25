package studio.demo.subarashi_jpop.remote.anime.model

data class AnimeListResponse(
    val pagination: Pagination,
    val data: List<AnimeResponseModel>
)