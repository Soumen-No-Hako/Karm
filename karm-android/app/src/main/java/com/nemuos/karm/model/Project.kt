package com.nemuos.karm.model

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class Project(
    @SerializedName("projectId")
    val projectId: String = "",

    @SerializedName("projectName")
    var projectName: String = "",

    @SerializedName("projectDescription")
    var projectDescription: String = "",

    @SerializedName("createdOn")
    val createdOn: String = Instant.now().toString(),

    @SerializedName("lastModifiedOn")
    var lastModifiedOn: String = Instant.now().toString(),

    @SerializedName("owner")
    var owner: String = "Nemuos",

    @SerializedName("projectStatus")
    var projectStatus: String = Status.NEW.displayName,

    @SerializedName("workItemIds")
    val workItemIds: MutableList<String> = mutableListOf(),

    @SerializedName("workItemCount")
    var workItemCount: Int = 0,

    @SerializedName("workitemContainerSize")
    var workitemContainerSize: Int = 0,

    // This field is not serialized but computed from workItemIds
    @Transient
    var workItems: List<WorkItem> = emptyList()
) {
    fun getStatusEnum(): Status = Status.fromString(projectStatus)

    fun updateStatus(newStatus: Status) {
        projectStatus = newStatus.displayName
        lastModifiedOn = Instant.now().toString()
    }

    fun addWorkItemId(workItemId: String) {
        workItemIds.add(workItemId)
        workItemCount = workItemIds.size
        lastModifiedOn = Instant.now().toString()
    }

    fun removeWorkItemId(workItemId: String) {
        workItemIds.remove(workItemId)
        workItemCount = workItemIds.size
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
