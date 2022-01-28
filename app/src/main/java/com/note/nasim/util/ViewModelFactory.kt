package com.note.nasim.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.note.nasim.data.repository.NoteRepository
import com.note.nasim.ui.viewmodel.NoteViewModel

class ViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }
        throw IllegalAccessException("Unknowns class!")
    }
}