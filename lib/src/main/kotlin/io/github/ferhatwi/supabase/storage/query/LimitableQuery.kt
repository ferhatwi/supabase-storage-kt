package io.github.ferhatwi.supabase.storage.query

import io.github.ferhatwi.supabase.storage.Sort

open class LimitableQuery internal constructor(
    bucketName: String,
    folderName: String,
    sort: Sort?,
    offset: Int?
) : LimitedQuery(bucketName, folderName, sort, offset, null) {
    fun limit(value: Int): LimitedQuery = apply { limit = value }
}