package com.nemuos.karm.model

enum class Status(val displayName: String) {
    NEW("NEW"),
    TODO("TO-DO"),
    IN_PROGRESS("IN-PROGRESS"),
    DONE("DONE"),
    CANCELED("CANCELED");

    companion object {
        fun fromString(value: String): Status {
            return entries.find { it.displayName == value } ?: TODO
        }
    }
}
