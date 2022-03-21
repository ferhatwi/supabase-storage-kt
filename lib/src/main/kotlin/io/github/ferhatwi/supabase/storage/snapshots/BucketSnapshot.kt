package io.github.ferhatwi.supabase.storage.snapshots

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class BucketSnapshot internal constructor(
    val name: String,
    val owner: String?,
    val public: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    internal constructor(map: Map<String, Any?>) : this(
        map["name"] as String,
        (map["owner"] as String).ifEmpty { null },
        map["public"] as Boolean,
        LocalDateTime.parse(map["created_at"] as String, DateTimeFormatter.ISO_DATE_TIME),
        LocalDateTime.parse(map["updated_at"] as String, DateTimeFormatter.ISO_DATE_TIME)
    )
}