package io.github.ferhatwi.supabase.storage

import io.github.ferhatwi.supabase.Supabase
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.utils.*
import io.ktor.http.*

internal fun storageURL() = "https://${Supabase.PROJECT_ID}.supabase.co/storage/v1"

internal fun formData(
    byteArray: ByteArray,
    contentType: String,
    cacheControl: Int?,
    upsert: Boolean
) = formData {
    append("file", byteArray, Headers.build {
        append(HttpHeaders.ContentType, contentType)
        append(HttpHeaders.ContentDisposition, "filename=random")
        if (cacheControl != null) append(HttpHeaders.CacheControl, "max-age=$cacheControl")
        if (upsert) append("x-upsert", "true")
    })

}

internal fun Sort.toMap(): Map<String, String> {
    return mapOf(
        "column" to sortable.toString(),
        "order" to sortBy.toString(),
    )
}

internal fun getClient(): HttpClient {
    return HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
}

internal suspend inline fun <reified T> HttpClient.request(
    url: String,
    method: HttpMethod,
    body: Any = EmptyContent,
    noinline headers: HeadersBuilder.() -> Unit = {}
): T {
    return request(url) {
        this.method = method
        this.body = body
        headers {
            authorize()
            headers()
        }
    }
}


internal fun HeadersBuilder.applicationJson() {
    append(HttpHeaders.ContentType, "application/json")
}

internal fun HttpRequestBuilder.authorize() {
    headers.append(HttpHeaders.Authorization, "Bearer ${Supabase.AUTHORIZATION}")
}

internal suspend inline fun <reified T> runCatching(
    block: () -> T,
    onSuccess: (T) -> Unit
) = runCatching { onSuccess(block()) }
    .getOrElse {
        when (it) {
            is ResponseException -> {
                val map: Map<String, Any?> = it.response.receive()
                val message = map["message"] as String?
                val code = map["code"] as String?
                val statusCode = it.response
                throw SupabaseStorageException("$message $code, $statusCode")
            }
            else -> throw it
        }
    }



