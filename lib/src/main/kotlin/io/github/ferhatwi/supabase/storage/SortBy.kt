package io.github.ferhatwi.supabase.storage

sealed class SortBy {
    override fun toString() = when (this) {
        Ascending -> "asc"
        Descending -> "desc"
    }

    object Ascending : SortBy()
    object Descending : SortBy()
}
