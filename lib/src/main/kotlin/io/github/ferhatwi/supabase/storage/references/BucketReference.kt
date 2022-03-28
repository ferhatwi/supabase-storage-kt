package io.github.ferhatwi.supabase.storage.references

import io.github.ferhatwi.supabase.storage.*
import io.github.ferhatwi.supabase.storage.snapshots.BucketSnapshot
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.flow

class BucketReference internal constructor(private val name: String) : FolderReference(name, "") {

    suspend fun get() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()

        runCatching<Map<String, Any?>>({
            getClient().request(url, HttpMethod.Get)
        }, onSuccess = {
            emit(BucketSnapshot(it))
        })
    }

    suspend fun makePublic() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        val map = mapOf("id" to name, "name" to name, "public" to true)

        runCatching<HttpResponse>({
            getClient().request(url, HttpMethod.Put, map) {
                applicationJson()
            }
        }, onSuccess = {
            emit(SupabaseStorageSuccess)
        })
    }

    suspend fun makePrivate() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        val map = mapOf("id" to name, "name" to name, "public" to false)

        runCatching<HttpResponse>({
            getClient().request(url, HttpMethod.Put, map) {
                applicationJson()
            }
        }, onSuccess = {
            emit(SupabaseStorageSuccess)
        })
    }

    suspend fun create(public: Boolean) = flow {
        val url = "${storageURL()}/bucket"
        val map = mapOf("id" to name, "name" to name, "public" to public)

        runCatching<HttpResponse>({
            getClient().request(url, HttpMethod.Post, map) {
                applicationJson()
            }
        }, onSuccess = {
            emit(SupabaseStorageSuccess)
        })
    }

    suspend fun empty() = flow {
        val url = "${storageURL()}/bucket/$name/empty".encodeURLPath()

        runCatching<HttpResponse>({
            getClient().request(url, HttpMethod.Post)
        }, onSuccess = {
            emit(SupabaseStorageSuccess)
        })
    }

    suspend fun delete() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()

        runCatching<Map<String, Any?>>({
            getClient().request(url, HttpMethod.Delete)
        }, onSuccess = {
            emit(BucketSnapshot(it))
        })
    }
}