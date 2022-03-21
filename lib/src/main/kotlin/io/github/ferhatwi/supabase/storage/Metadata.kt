package io.github.ferhatwi.supabase.storage

class Metadata internal constructor(private val map: Map<String, Any?>) {
    override fun toString(): String {
        return map.toString()
    }

    fun get(key: String) = map[key]

}