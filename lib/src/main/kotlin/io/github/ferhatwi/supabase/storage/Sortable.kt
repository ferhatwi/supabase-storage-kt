package io.github.ferhatwi.supabase.storage

sealed class Sortable {

    override fun toString() = when (this) {
        Name -> "name"
        ID -> "id"
        CreatedAt -> "created_at"
        UpdatedAt -> "updated_at"
        LastAccessedAt -> "last_accessed_at"
    }

    object Name : Sortable()
    object ID : Sortable()
    object CreatedAt : Sortable()
    object UpdatedAt : Sortable()
    object LastAccessedAt : Sortable()
}