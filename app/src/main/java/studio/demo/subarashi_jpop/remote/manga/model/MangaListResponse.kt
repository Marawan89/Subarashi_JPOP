package studio.demo.subarashi_jpop.remote.manga.model

data class MangaListResponse(
    val pagination: Pagination,
    val data: List<MangaResponseModel>
)