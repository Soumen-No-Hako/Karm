package com.nemuos.karm

import android.app.Application
import com.nemuos.karm.storage.DataManager

class KarmApplication : Application() {
    lateinit var dataManager: DataManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataManager = DataManager(this)
    }

    companion object {
        private lateinit var instance: KarmApplication

        fun getInstance(): KarmApplication = instance
    }
}
