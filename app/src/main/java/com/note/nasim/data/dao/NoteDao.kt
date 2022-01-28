package com.note.nasim.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.note.nasim.data.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(item: Note)

    @Query("DELETE FROM tb_notes WHERE id=:id")
    fun deleteNotes(id: Int)

    @Query("SELECT * FROM tb_notes ORDER BY ID DESC")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM tb_notes WHERE priority=:priority ORDER BY ID DESC")
    fun getNotesByPriority(priority: Int): LiveData<List<Note>>


}