package studio.demo.subarashi_jpop.remote.manga.model

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val items: Items
)