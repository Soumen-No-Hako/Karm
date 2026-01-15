package com.nemuos.karm.storage

import android.content.Context
import com.nemuos.karm.model.Project
import com.nemuos.karm.model.Status
import com.nemuos.karm.model.WorkItem
import java.time.Instant
import java.util.UUID

class DataManager(context: Context) {
    private val storage = KarmStorage(context)
    private val projects = mutableListOf<Project>()
    private val workItems = mutableListOf<WorkItem>()

    init {
        loadData()
    }

    private fun loadData() {
        projects.clear()
        workItems.clear()
        projects.addAll(storage.loadProjects())
        workItems.addAll(storage.loadWorkItems())

        // Populate workItems for each project
        projects.forEach { project ->
            project.workItems = workItems.filter { it.workItemId in project.workItemIds }
        }
    }

    fun getProjects(): List<Project> = projects.toList()

    fun getProject(projectId: String): Project? {
        return projects.find { it.projectId == projectId }
    }

    fun createProject(name: String, description: String): Project {
        val projectId = generateId()
        val project = Project(
            projectId = projectId,
            projectName = name,
            projectDescription = description,
            createdOn = Instant.now().toString(),
            lastModifiedOn = Instant.now().toString(),
            projectStatus = Status.NEW.displayName,
            workItemCount = 0
        )
        projects.add(project)
        storage.saveProjects(projects)
        return project
    }

    fun updateProject(project: Project) {
        val index = projects.indexOfFirst { it.projectId == project.projectId }
        if (index != -1) {
            project.lastModifiedOn = Instant.now().toString()
            projects[index] = project
            storage.saveProjects(projects)
        }
    }

    fun deleteProject(projectId: String) {
        val project = projects.find { it.projectId == projectId }
        project?.let {
            // Delete all associated work items
            it.workItemIds.forEach { workItemId ->
                deleteWorkItem(workItemId)
            }
            projects.remove(it)
            storage.saveProjects(projects)
        }
    }

    fun getWorkItems(): List<WorkItem> = workItems.toList()

    fun getWorkItem(workItemId: String): WorkItem? {
        return workItems.find { it.workItemId == workItemId }
    }

    fun getWorkItemsForProject(projectId: String): List<WorkItem> {
        val project = getProject(projectId) ?: return emptyList()
        return workItems.filter { it.workItemId in project.workItemIds }
    }

    fun createWorkItem(projectId: String, title: String, description: String): WorkItem? {
        val project = projects.find { it.projectId == projectId } ?: return null

        val workItemId = "$projectId-${project.workitemContainerSize + 1}"
        val workItem = WorkItem(
            workItemId = workItemId,
            workItemTitle = title,
            workItemDescription = description,
            status = Status.TODO.displayName,
            createdOn = Instant.now().toString(),
            lastModifiedOn = Instant.now().toString()
        )

        workItems.add(workItem)
        project.addWorkItemId(workItemId)
        project.workitemContainerSize += 1

        storage.saveAll(projects, workItems)

        // Update project's workItems list
        project.workItems = getWorkItemsForProject(projectId)

        return workItem
    }

    fun updateWorkItem(workItem: WorkItem) {
        val index = workItems.indexOfFirst { it.workItemId == workItem.workItemId }
        if (index != -1) {
            workItem.lastModifiedOn = Instant.now().toString()
            workItems[index] = workItem
            storage.saveWorkItems(workItems)

            // Update the project's lastModifiedOn as well
            val projectId = workItem.workItemId.substringBeforeLast("-")
            val project = projects.find { it.projectId == projectId }
            project?.let {
                it.lastModifiedOn = Instant.now().toString()
                storage.saveProjects(projects)
            }
        }
    }

    fun deleteWorkItem(workItemId: String) {
        val workItem = workItems.find { it.workItemId == workItemId }
        workItem?.let {
            workItems.remove(it)

            // Remove from project
            val projectId = workItemId.substringBeforeLast("-")
            val project = projects.find { it.projectId == projectId }
            project?.let { proj ->
                proj.removeWorkItemId(workItemId)
                proj.workItems = getWorkItemsForProject(projectId)
            }

            storage.saveAll(projects, workItems)
        }
    }

    fun updateProjectStatus(projectId: String, status: Status) {
        val project = projects.find { it.projectId == projectId }
        project?.let {
            it.updateStatus(status)
            storage.saveProjects(projects)
        }
    }

    fun updateWorkItemStatus(workItemId: String, status: Status) {
        val workItem = workItems.find { it.workItemId == workItemId }
        workItem?.let {
            it.updateStatus(status)
            storage.saveWorkItems(workItems)

            // Update project's lastModifiedOn
            val projectId = workItemId.substringBeforeLast("-")
            val project = projects.find { it.projectId == projectId }
            project?.let { proj ->
                proj.lastModifiedOn = Instant.now().toString()
                storage.saveProjects(projects)
            }
        }
    }

    fun addComment(workItemId: String, comment: String) {
        val workItem = workItems.find { it.workItemId == workItemId }
        workItem?.let {
            it.addComment(comment)
            storage.saveWorkItems(workItems)

            // Update project's lastModifiedOn
            val projectId = workItemId.substringBeforeLast("-")
            val project = projects.find { it.projectId == projectId }
            project?.let { proj ->
                proj.lastModifiedOn = Instant.now().toString()
                storage.saveProjects(projects)
            }
        }
    }

    fun sortProjects(sortType: SortType): List<Project> {
        return when (sortType) {
            SortType.LAST_MODIFIED -> projects.sortedByDescending { it.lastModifiedOn }
            SortType.STATUS -> projects.sortedBy { it.projectStatus }
            SortType.WORK_ITEM_COUNT -> projects.sortedByDescending { it.workItemCount }
            SortType.NAME -> projects.sortedBy { it.projectName }
        }
    }

    fun sortWorkItems(workItems: List<WorkItem>, sortType: SortType): List<WorkItem> {
        return when (sortType) {
            SortType.LAST_MODIFIED -> workItems.sortedByDescending { it.lastModifiedOn }
            SortType.STATUS -> workItems.sortedBy { it.status }
            else -> workItems
        }
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    enum class SortType {
        LAST_MODIFIED,
        STATUS,
        WORK_ITEM_COUNT,
        NAME
    }
}
