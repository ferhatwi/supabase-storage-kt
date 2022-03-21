package io.github.ferhatwi.supabase.storage.snapshots

import io.github.ferhatwi.supabase.storage.Metadata
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FileSnapshot(
    val name: String,
    val id: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastAccessedAt: LocalDateTime,
    val metadata: Metadata?
) {

    internal constructor(map: Map<String, Any?>) : this(map["name"] as String,
        map["id"] as String,
        LocalDateTime.parse(map["created_at"] as String, DateTimeFormatter.ISO_DATE_TIME),
        LocalDateTime.parse(map["updated_at"] as String, DateTimeFormatter.ISO_DATE_TIME),
        LocalDateTime.parse(map["last_accessed_at"] as String, DateTimeFormatter.ISO_DATE_TIME),
        (map["metadata"] as Map<String, Any?>?).let { if (it == null) null else Metadata(it) }
    )
}