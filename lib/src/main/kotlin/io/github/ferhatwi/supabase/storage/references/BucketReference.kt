package io.github.ferhatwi.supabase.storage.references

import io.github.ferhatwi.supabase.storage.*
import io.github.ferhatwi.supabase.storage.snapshots.BucketSnapshot
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.flow

class BucketReference internal constructor(private val name: String) : FolderReference(name, "") {

    suspend fun get() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()

        getClient().request<Map<String, Any?>>(url, HttpMethod.Get).also {
            emit(BucketSnapshot(it))
        }
    }

    suspend fun makePublic() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        val map = mapOf("id" to name, "name" to name, "public" to true)

        getClient().request<HttpResponse>(url, HttpMethod.Put, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun makePrivate() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        val map = mapOf("id" to name, "name" to name, "public" to false)

        getClient().request<HttpResponse>(url, HttpMethod.Put, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun create(public: Boolean) = flow {
        val url = "${storageURL()}/bucket"
        val map = mapOf("id" to name, "name" to name, "public" to public)

        getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun empty() = flow {
        val url = "${storageURL()}/bucket/$name/empty".encodeURLPath()

        getClient().request<HttpResponse>(url, HttpMethod.Post).also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun delete() = flow {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()

        getClient().request<Map<String, Any?>>(url, HttpMethod.Delete).also {
            emit(BucketSnapshot(it))
        }
    }
}