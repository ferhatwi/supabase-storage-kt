package io.github.ferhatwi.supabase.storage.query

import io.github.ferhatwi.supabase.storage.*
import io.github.ferhatwi.supabase.storage.references.FolderReference
import io.github.ferhatwi.supabase.storage.snapshots.FileSnapshot
import io.ktor.http.*
import kotlinx.coroutines.flow.flow

open class LimitedQuery internal constructor(
    internal val bucketName: String,
    internal val folderName: String,
    internal var sort: Sort?,
    internal var offset: Int?,
    internal var limit: Int?
) {

    private suspend fun list() = flow {
        val url = "${storageURL()}/object/list/$bucketName".encodeURLPath()

        val map = hashMapOf<String, Any?>("prefix" to folderName)
        limit?.let {
            map["limit"] = it
        }
        offset?.let {
            map["offset"] = it
        }
        sort?.let {
            map["sortBy"] = it.toMap()
        }

        getClient().request<List<Map<String, Any?>>>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(it)
        }
    }

    suspend fun listFiles() = flow {
        list().collect {
            emit(it.toMutableList().filter { it["id"] != null }.map { FileSnapshot(it) })
        }
    }

    suspend fun listFolders() = flow {
        list().collect {
            emit(it.toMutableList().filter { it["id"] == null }
                .map { FolderReference(bucketName, it["name"] as String) })
        }
    }
}