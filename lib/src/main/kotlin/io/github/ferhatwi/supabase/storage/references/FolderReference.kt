package io.github.ferhatwi.supabase.storage.references

import io.github.ferhatwi.supabase.storage.query.SortableQuery

open class FolderReference internal constructor(
    bucketName: String,
    folderName: String
) : SortableQuery(bucketName, folderName) {

    override fun toString(): String {
        return "$bucketName/$folderName"
    }

    fun folder(name: String) =
        FolderReference(bucketName, "${folderName}${if (folderName.isEmpty()) "" else "/"}$name")

    fun file(name: String) = FileReference(bucketName, folderName, name)

}