package io.github.ferhatwi.supabase.storage

import io.github.ferhatwi.supabase.Supabase
import io.github.ferhatwi.supabase.storage.references.BucketReference
import io.github.ferhatwi.supabase.storage.snapshots.BucketSnapshot
import io.ktor.http.*
import java.time.LocalDateTime

class SupabaseStorage {

    companion object {
        fun getInstance() = SupabaseStorage()
    }


    suspend fun bucketList(): List<BucketSnapshot> {
        val url = "${storageURL()}/bucket"
        val result: List<Map<String, Any?>> = getClient().request(url, HttpMethod.Get)

        return result.map {
            BucketSnapshot(
                it["name"] as String,
                (it["owner"] as String).ifEmpty { null },
                it["public"] as Boolean,
                LocalDateTime.parse(it["created_at"] as String),
                LocalDateTime.parse(it["updated_at"] as String)
            )
        }

    }

    fun bucket(name: String) = BucketReference(name)

}

fun Supabase.storage(): SupabaseStorage = SupabaseStorage.getInstance()