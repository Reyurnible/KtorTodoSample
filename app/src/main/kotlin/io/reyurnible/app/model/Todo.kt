package io.reyurnible.app.model

import org.joda.time.DateTime

data class Todo(
        val id: Int,
        val title: String,
        val isDone: Boolean,
        val timestamp: DateTime
)