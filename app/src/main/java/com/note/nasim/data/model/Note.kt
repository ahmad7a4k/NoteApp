package com.note.nasim.data.model

import android.os.Parcelable
import android.text.TextUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_notes")
@Parcelize
class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var subtitle: String,
    var description: String,
    var date: String,
    var priority: Int
) : Parcelable {
    fun inputValidate(): Int {
        if (TextUtils.isEmpty(title)) {
            return 1
        } else if (TextUtils.isEmpty(subtitle)) {
            return 2
        } else if (TextUtils.isEmpty(description)) {
            return 3
        } else {
            return 0
        }
    }
}