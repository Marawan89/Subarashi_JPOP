package studio.demo.subarashi_jpop.remote.manga.model

import studio.demo.subarashi_jpop.remote.anime.model.Pagination

data class MangaListResponse(
    val data: List<MangaModel>,
    val pagination: Pagination
)