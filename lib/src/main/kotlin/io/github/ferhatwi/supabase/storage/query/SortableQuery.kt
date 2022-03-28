package io.github.ferhatwi.supabase.storage.query

import io.github.ferhatwi.supabase.storage.Sort
import io.github.ferhatwi.supabase.storage.SortBy
import io.github.ferhatwi.supabase.storage.Sortable

open class SortableQuery internal constructor(bucketName: String, folderName: String) :
    OffsetableQuery(bucketName, folderName, null) {
    fun sort(sortable: Sortable, sortBy: SortBy): OffsetableQuery =
        apply { sort = Sort(sortable, sortBy) }
}