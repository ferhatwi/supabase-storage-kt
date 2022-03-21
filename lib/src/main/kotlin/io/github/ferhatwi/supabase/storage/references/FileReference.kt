package io.github.ferhatwi.supabase.storage.references

import io.github.ferhatwi.supabase.Supabase
import io.github.ferhatwi.supabase.storage.applicationJson
import io.github.ferhatwi.supabase.storage.getClient
import io.github.ferhatwi.supabase.storage.request
import io.github.ferhatwi.supabase.storage.storageURL
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File

class FileReference internal constructor(
    private val bucketName: String,
    private val folderPath: String,
    private val name: String
) {

    private val pathAfterBucket = "$folderPath${if (folderPath.isEmpty()) "" else "/"}$name"


    suspend fun upload(
        file: File,
        contentType: String,
        cacheControl: Int? = null,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(
                url,
                HttpMethod.Post,
                MultiPartFormDataContent(
                    io.github.ferhatwi.supabase.storage.formData(
                        file.readBytes(),
                        contentType,
                        cacheControl,
                        false
                    )
                )
            )
            onSuccess()
        }, onFailure)
    }

    suspend fun upload(
        data: Any?,
        contentType: String,
        cacheControl: Int? = null,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(
                url,
                HttpMethod.Post,
                MultiPartFormDataContent(
                    io.github.ferhatwi.supabase.storage.formData(
                        data.toString().toByteArray(),
                        contentType,
                        cacheControl,
                        false
                    )
                )
            )
            onSuccess()
        }, onFailure)
    }


    suspend fun upsert(
        file: File,
        contentType: String,
        cacheControl: Int? = null,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(
                url,
                HttpMethod.Post,
                MultiPartFormDataContent(
                    io.github.ferhatwi.supabase.storage.formData(
                        file.readBytes(),
                        contentType,
                        cacheControl,
                        true
                    )
                )
            )
            onSuccess()
        }, onFailure)
    }

    suspend fun upsert(
        data: Any?, contentType: String,
        cacheControl: Int? = null,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(
                url,
                HttpMethod.Post,
                MultiPartFormDataContent(
                    io.github.ferhatwi.supabase.storage.formData(
                        data.toString().toByteArray(),
                        contentType,
                        cacheControl,
                        true
                    )
                )
            )
            onSuccess()
        }, onFailure)
    }

    suspend fun get(onFailure: (HttpStatusCode) -> Unit = {}, onSuccess: (ByteArray) -> Unit = {}) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()
        io.github.ferhatwi.supabase.storage.runCatching({
            val result: HttpResponse = getClient().request(url, HttpMethod.Get)
            onSuccess(result.receive())
        }, onFailure)
    }

    suspend fun saveTo(
        destination: String,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val file = File(destination)
        get(onFailure, onSuccess = {
            file.writeBytes(it)
            onSuccess()
        })
    }

    suspend fun update(
        file: File,
        contentType: String,
        cacheControl: Int? = null,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(
                url,
                HttpMethod.Put,
                MultiPartFormDataContent(
                    io.github.ferhatwi.supabase.storage.formData(
                        file.readBytes(),
                        contentType,
                        cacheControl,
                        false
                    )
                )
            )
            onSuccess()
        }, onFailure)
    }

    suspend fun update(
        data: Any?, contentType: String,
        cacheControl: Int,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(
                url,
                HttpMethod.Put,
                MultiPartFormDataContent(
                    io.github.ferhatwi.supabase.storage.formData(
                        data.toString().toByteArray(),
                        contentType,
                        cacheControl,
                        false
                    )
                )
            )
            onSuccess()
        }, onFailure)
    }

    suspend fun moveFromHere(
        to: BucketReference.() -> FileReference,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val bucketReference = BucketReference(bucketName)
        if (to(bucketReference).bucketName != bucketName) {
            throw Exception("Bucket name ${to(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/move"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to pathAfterBucket,
            "destinationKey" to to(bucketReference).run { pathAfterBucket }
        )
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }


    suspend fun moveToHere(
        from: BucketReference.() -> FileReference,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val bucketReference = BucketReference(bucketName)
        if (from(bucketReference).bucketName != bucketName) {
            throw Exception("Bucket name ${from(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/move"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to from(bucketReference).run { pathAfterBucket },
            "destinationKey" to pathAfterBucket
        )
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }


    suspend fun copyFromHere(
        to: BucketReference.() -> FileReference,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val bucketReference = BucketReference(bucketName)
        if (to(bucketReference).bucketName != bucketName) {
            throw Exception("Bucket name ${to(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/copy"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to pathAfterBucket,
            "destinationKey" to to(bucketReference).run { pathAfterBucket }
        )
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }


    suspend fun copyToHere(
        from: BucketReference.() -> FileReference,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val bucketReference = BucketReference(bucketName)
        if (from(bucketReference).bucketName != bucketName) {
            throw Exception("Bucket name ${from(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/copy"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to from(bucketReference).run { pathAfterBucket },
            "destinationKey" to pathAfterBucket
        )
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }

    suspend fun createSignedURL(
        expiresIn: Int,
        onFailure: (HttpStatusCode) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        val url = "${storageURL()}/object/sign/$bucketName/$pathAfterBucket".encodeURLPath()
        val map = hashMapOf<String, Any?>("expiresIn" to expiresIn)
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }


    suspend fun remove(onFailure: (HttpStatusCode) -> Unit = {}, onSuccess: () -> Unit = {}) {
        val url = "${storageURL()}/object/$bucketName"

        val map = mapOf<String, Any?>("prefixes" to arrayOf(pathAfterBucket))
        io.github.ferhatwi.supabase.storage.runCatching({
            getClient().request<HttpResponse>(url, HttpMethod.Delete, map) {
                applicationJson()
            }
            onSuccess()
        }, onFailure)
    }

    fun getPublicUrl() =
        "https://${Supabase.PROJECT_ID}.supabase.co/storage/v1/object/public/$bucketName/$pathAfterBucket".encodeURLPath()

}