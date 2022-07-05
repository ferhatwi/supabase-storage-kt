package io.github.ferhatwi.supabase.storage

import io.github.ferhatwi.supabase.Supabase
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

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
        install(ContentNegotiation) {
            gson()
        }
    }
}

internal suspend inline fun <reified T> HttpClient.request(
    url: String,
    method: HttpMethod,
    body: Any = EmptyContent,
    noinline headers: HeadersBuilder.() -> Unit = {}
): T = request(url) {
    this.method = method
    setBody(body)
    headers {
        authorize()
        headers()
    }
}.body()


internal fun HeadersBuilder.applicationJson() {
    append(HttpHeaders.ContentType, "application/json")
}

internal fun HttpRequestBuilder.authorize() {
    headers.append(HttpHeaders.Authorization, "Bearer ${Supabase.AUTHORIZATION}")
}




