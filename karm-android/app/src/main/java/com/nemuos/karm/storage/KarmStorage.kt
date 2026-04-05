package com.nemuos.karm.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nemuos.karm.model.Project
import com.nemuos.karm.model.WorkItem
import java.io.File
import java.io.IOException

class KarmStorage(private val context: Context) {
    private val gson = Gson()
    private val projectsFileName = "projects.json"
    private val workItemsFileName = "workitems.json"

    private val karmDataDir: File by lazy {
        val dir = File(context.filesDir, "karm_data")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir
    }

    private val projectsFile: File
        get() = File(karmDataDir, projectsFileName)

    private val workItemsFile: File
        get() = File(karmDataDir, workItemsFileName)

    fun loadProjects(): List<Project> {
        return try {
            if (!projectsFile.exists()) {
                projectsFile.createNewFile()
                emptyList()
            } else {
                val jsonString = projectsFile.readText()
                if (jsonString.isBlank()) {
                    emptyList()
                } else {
                    val type = object : TypeToken<List<Project>>() {}.type
                    gson.fromJson(jsonString, type) ?: emptyList()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun saveProjects(projects: List<Project>) {
        try {
            val jsonString = gson.toJson(projects)
            projectsFile.writeText(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadWorkItems(): List<WorkItem> {
        return try {
            if (!workItemsFile.exists()) {
                workItemsFile.createNewFile()
                emptyList()
            } else {
                val jsonString = workItemsFile.readText()
                if (jsonString.isBlank()) {
                    emptyList()
                } else {
                    val type = object : TypeToken<List<WorkItem>>() {}.type
                    gson.fromJson(jsonString, type) ?: emptyList()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun saveWorkItems(workItems: List<WorkItem>) {
        try {
            val jsonString = gson.toJson(workItems)
            workItemsFile.writeText(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun saveAll(projects: List<Project>, workItems: List<WorkItem>) {
        saveProjects(projects)
        saveWorkItems(workItems)
    }

    fun clearAllData() {
        try {
            projectsFile.delete()
            workItemsFile.delete()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getStorageInfo(): StorageInfo {
        return StorageInfo(
            dataDirectory = karmDataDir.absolutePath,
            projectsFileExists = projectsFile.exists(),
            workItemsFileExists = workItemsFile.exists(),
            projectsFileSize = if (projectsFile.exists()) projectsFile.length() else 0,
            workItemsFileSize = if (workItemsFile.exists()) workItemsFile.length() else 0
        )
    }

    data class StorageInfo(
        val dataDirectory: String,
        val projectsFileExists: Boolean,
        val workItemsFileExists: Boolean,
        val projectsFileSize: Long,
        val workItemsFileSize: Long
    )
}
