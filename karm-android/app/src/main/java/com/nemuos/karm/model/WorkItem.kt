package com.nemuos.karm.model

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class WorkItem(
    @SerializedName("workItemId")
    val workItemId: String = "",

    @SerializedName("workItemTitle")
    var workItemTitle: String = "",

    @SerializedName("workItemDescription")
    var workItemDescription: String = "",

    @SerializedName("status")
    var status: String = Status.TODO.displayName,

    @SerializedName("createdOn")
    val createdOn: String = Instant.now().toString(),

    @SerializedName("lastModifiedOn")
    var lastModifiedOn: String = Instant.now().toString(),

    @SerializedName("comments")
    val comments: MutableList<String> = mutableListOf()
) {
    fun addComment(comment: String) {
        val timestamp = Instant.now().toString().substring(0, 10)
        comments.add("$timestamp -> $comment")
        lastModifiedOn = Instant.now().toString()
    }

    fun getStatusEnum(): Status = Status.fromString(status)

    fun updateStatus(newStatus: Status) {
        status = newStatus.displayName
        lastModifiedOn = Instant.now().toString()
    }

    fun getFormattedCreatedDate(): String {
        return try {
            createdOn.substring(0, 10)
        } catch (e: Exception) {
            createdOn
        }
    }

    fun getFormattedLastModifiedDate(): String {
        return try {
            lastModifiedOn.substring(0, 10)
        } catch (e: Exception) {
            lastModifiedOn
        }
    }
}
