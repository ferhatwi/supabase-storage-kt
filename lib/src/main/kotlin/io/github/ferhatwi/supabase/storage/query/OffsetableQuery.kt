package io.github.ferhatwi.supabase.storage.query

import io.github.ferhatwi.supabase.storage.Sort

open class OffsetableQuery internal constructor(
    bucketName: String,
    folderName: String,
    sort: Sort?
) : LimitableQuery(bucketName, folderName, sort, null) {
    fun offset(value: Int) : LimitableQuery = apply { offset = value }
}