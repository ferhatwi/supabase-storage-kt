package io.github.ferhatwi.supabase.storage.references

import io.github.ferhatwi.supabase.storage.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.flow
import java.io.File

class FileReference internal constructor(
    private val bucketName: String,
    folderPath: String,
    name: String
) {

    private val pathAfterBucket = "$folderPath${if (folderPath.isEmpty()) "" else "/"}$name"

    suspend fun upload(
        file: File,
        contentType: String,
        cacheControl: Int? = null
    ) = upload(file.readBytes(), contentType, cacheControl)

    suspend fun upload(
        data: Any?,
        contentType: String,
        cacheControl: Int? = null
    ) = upload(data.toString().toByteArray(), contentType, cacheControl)

    private suspend fun upload(
        byteArray: ByteArray,
        contentType: String,
        cacheControl: Int? = null
    ) = flow {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        getClient().request<HttpResponse>(
            url,
            HttpMethod.Post,
            MultiPartFormDataContent(
                formData(byteArray, contentType, cacheControl, false)
            )
        ).also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun upsert(
        file: File,
        contentType: String,
        cacheControl: Int? = null
    ) = upsert(file.readBytes(), contentType, cacheControl)

    suspend fun upsert(
        data: Any?,
        contentType: String,
        cacheControl: Int? = null
    ) = upsert(data.toString().toByteArray(), contentType, cacheControl)

    private suspend fun upsert(
        byteArray: ByteArray,
        contentType: String,
        cacheControl: Int? = null
    ) = flow {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        getClient().request<HttpResponse>(
            url,
            HttpMethod.Post,
            MultiPartFormDataContent(
                formData(byteArray, contentType, cacheControl, true)
            )
        ).also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun get() = flow {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        getClient().request<ByteArray>(url, HttpMethod.Get).also {
            emit(it)
        }
    }

    suspend fun saveTo(destination: String) = flow {
        get().collect {
            emit(File(destination).writeBytes(it))
        }
    }

    suspend fun update(
        file: File,
        contentType: String,
        cacheControl: Int? = null
    ) = update(file.readBytes(), contentType, cacheControl)

    suspend fun update(
        data: Any?,
        contentType: String,
        cacheControl: Int? = null
    ) = update(data.toString().toByteArray(), contentType, cacheControl)

    private suspend fun update(
        byteArray: ByteArray,
        contentType: String,
        cacheControl: Int? = null
    ) = flow {
        val url = "${storageURL()}/object/$bucketName/$pathAfterBucket".encodeURLPath()

        getClient().request<HttpResponse>(
            url,
            HttpMethod.Put,
            MultiPartFormDataContent(
                formData(byteArray, contentType, cacheControl, false)
            )
        ).also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun moveFromHere(to: BucketReference.() -> FileReference) = flow {
        val bucketReference = BucketReference(bucketName)
        if (to(bucketReference).bucketName != bucketName) {
            throw Exception("${to(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/move"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to pathAfterBucket,
            "destinationKey" to to(bucketReference).run { pathAfterBucket }
        )

        getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun moveToHere(from: BucketReference.() -> FileReference) = flow {
        val bucketReference = BucketReference(bucketName)
        if (from(bucketReference).bucketName != bucketName) {
            throw Exception("${from(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/move"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to from(bucketReference).run { pathAfterBucket },
            "destinationKey" to pathAfterBucket
        )

        getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun copyFromHere(to: BucketReference.() -> FileReference) = flow {
        val bucketReference = BucketReference(bucketName)
        if (to(bucketReference).bucketName != bucketName) {
            throw Exception("${to(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/copy"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to pathAfterBucket,
            "destinationKey" to to(bucketReference).run { pathAfterBucket }
        )

        getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun copyToHere(from: BucketReference.() -> FileReference) = flow {
        val bucketReference = BucketReference(bucketName)
        if (from(bucketReference).bucketName != bucketName) {
            throw Exception("${from(bucketReference).bucketName} does not match $bucketName")
        }

        val url = "${storageURL()}/object/copy"
        val map = hashMapOf<String, Any?>(
            "bucketId" to bucketName,
            "sourceKey" to from(bucketReference).run { pathAfterBucket },
            "destinationKey" to pathAfterBucket
        )

        getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun createSignedURL(expiresIn: Int) = flow {
        val url = "${storageURL()}/object/sign/$bucketName/$pathAfterBucket".encodeURLPath()
        val map = hashMapOf<String, Any?>("expiresIn" to expiresIn)

        getClient().request<HttpResponse>(url, HttpMethod.Post, map) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    suspend fun remove() = flow {
        val url = "${storageURL()}/object/$bucketName"

        getClient().request<HttpResponse>(url, HttpMethod.Delete) {
            applicationJson()
        }.also {
            emit(SupabaseStorageSuccess)
        }
    }

    fun getPublicUrl() =
        "${storageURL()}//object/public/$bucketName/$pathAfterBucket".encodeURLPath()
}