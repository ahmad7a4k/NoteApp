package com.note.nasim.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.note.nasim.data.model.Note
import com.note.nasim.data.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    fun insertOrUpdateNote(notes: Note) {
        repository.insertOrUpdateNote(notes)
    }

    fun getNotes(): LiveData<List<Note>> {
        return repository.getNotes()
    }

    fun getNotesByPriority(priority: Int): LiveData<List<Note>> {
        return repository.getNotesByPriority(priority)
    }

    fun deleteNotes(id: Int) {
        repository.deleteNote(id)
    }


}
