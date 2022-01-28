package com.note.nasim.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.note.nasim.data.dao.NoteDao
import com.note.nasim.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDB : RoomDatabase() {

    abstract fun notesDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDB? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDB(context).also {
                INSTANCE = it
            }
        }

        private fun buildDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDB::class.java,
            "db_note"
        ).allowMainThreadQueries().build()
    }


}