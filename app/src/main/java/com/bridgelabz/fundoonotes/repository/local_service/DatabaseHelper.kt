package com.bridgelabz.fundoonotes.repository.local_service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManagerImpl.Companion.CREATE_TABLE_LABEL
import com.bridgelabz.fundoonotes.repository.local_service.note_label_relation_module.NoteLabelTableManagerImpl.Companion.CREATE_TABLE_NOTE_LABEL
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl.NoteEntry.CREATE_NOTE_TABLE
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl.UserEntry.CREATE_USER_TABLE

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

    /**Function to create new User database*/
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_NOTE_TABLE)
        db.execSQL(CREATE_TABLE_LABEL)
        db.execSQL(CREATE_TABLE_NOTE_LABEL)
    }

    /**Function to upgrade the existing user database*/
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        if (DATABASE_VERSION > VERSION_ONE) {
//            db.execSQL(CREATE_USER_TABLE)
//        }
//        if (DATABASE_VERSION < VERSION_TWO) {
//            db.execSQL(CREATE_NOTE_TABLE)
//        }
//        if (DATABASE_VERSION < VERSION_THREE) {
//            db.execSQL(CREATE_TABLE_LABEL)
//        }
//        if (DATABASE_VERSION < VERSION_FOUR)
//            db.execSQL(CREATE_TABLE_NOTE_LABEL)
        when {
            DATABASE_VERSION < VERSION_TWO -> db.execSQL(CREATE_NOTE_TABLE)
            DATABASE_VERSION < VERSION_THREE -> db.execSQL(CREATE_TABLE_LABEL)
            DATABASE_VERSION < VERSION_FOUR -> db.execSQL(CREATE_TABLE_NOTE_LABEL)
        }
    }

    /**Function to Downgrade*/
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    /**Function to open database to write*/
    fun open(): SQLiteDatabase {
        return this.writableDatabase
    }

    companion object {
        const val VERSION_ONE = 1
        const val VERSION_TWO = 2
        const val VERSION_THREE = 3
        const val VERSION_FOUR = 4
        const val VERSION_FIVE = 5
        const val DATABASE_VERSION = VERSION_FIVE
        const val DATABASE_NAME = "App.db"
    }
}