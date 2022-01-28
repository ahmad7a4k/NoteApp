package com.note.nasim.data.repository

import androidx.lifecycle.LiveData
import com.note.nasim.data.dao.NoteDao
import com.note.nasim.data.model.Note

class NoteRepository(private val dao: NoteDao) {
    fun getNotes(): LiveData<List<Note>> = dao.getNotes()
    fun getNotesByPriority(priority:Int): LiveData<List<Note>> = dao.getNotesByPriority(priority)

    fun insertOrUpdateNote(notes: Note) {
        dao.addNote(notes)
    }

    fun deleteNote(id: Int) {
        dao.deleteNotes(id)
    }

}