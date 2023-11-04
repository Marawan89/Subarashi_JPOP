package studio.demo.subarashi_jpop.remote.model

data class AnimeListResponse(
    val pagination: Pagination,
    val data: List<AnimeModel>
)