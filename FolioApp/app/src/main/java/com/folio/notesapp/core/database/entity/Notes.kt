package com.folio.notesapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val folder: String,
    val tags: String,
    @ColumnInfo(name = "created_time") val createdTime: String,
    @ColumnInfo(name = "modified_time") val modifiedTime: String
)
