package io.github.ferhatwi.supabase.storage.references

import io.github.ferhatwi.supabase.storage.applicationJson
import io.github.ferhatwi.supabase.storage.getClient
import io.github.ferhatwi.supabase.storage.request
import io.github.ferhatwi.supabase.storage.snapshots.BucketSnapshot
import io.github.ferhatwi.supabase.storage.storageURL
import io.ktor.client.statement.*
import io.ktor.http.*

class BucketReference internal constructor(private val name: String) : FolderReference(name, "") {

    suspend fun get(
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: (BucketSnapshot) -> Unit = {}
    ) {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()

        io.github.ferhatwi.supabase.storage.runCatching({
            val result: Map<String, Any?> = getClient().request(url, HttpMethod.Get)
            onSuccess(BucketSnapshot(result))
        }, onFailure)
    }

    suspend fun makePublic(
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        val map = mapOf("id" to name, "name" to name, "public" to true)
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Put, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }

    suspend fun makePrivate(
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        val map = mapOf("id" to name, "name" to name, "public" to false)
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Put, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }

    suspend fun create(
        public: Boolean,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/bucket"
        val map = mapOf("id" to name, "name" to name, "public" to public)
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }


    suspend fun empty(
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/bucket/$name/empty".encodeURLPath()
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post)
            onSuccess()
        }, onFailure)
    }

    suspend fun delete(
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: (BucketSnapshot) -> Unit = {}
    ) {
        val url = "${storageURL()}/bucket/$name".encodeURLPath()
        io.github.ferhatwi.supabase.storage.runCatching({
            val result: Map<String, Any?> = getClient().request(url, HttpMethod.Delete)
            onSuccess(BucketSnapshot(result))
        }, onFailure)
    }

}