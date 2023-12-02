package studio.demo.subarashi_jpop.remote.manga.model

import studio.demo.subarashi_jpop.remote.anime.model.Pagination

data class MangaListResponse(
    val pagination: Pagination,
    val data: List<MangaResponseModel>
)