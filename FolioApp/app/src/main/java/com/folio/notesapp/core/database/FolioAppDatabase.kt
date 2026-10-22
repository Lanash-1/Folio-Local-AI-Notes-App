package com.folio.notesapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.folio.notesapp.core.database.dao.NotesDao
import com.folio.notesapp.core.database.entity.Notes

@Database(entities = [Notes::class], version = 1)
abstract class FolioAppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}