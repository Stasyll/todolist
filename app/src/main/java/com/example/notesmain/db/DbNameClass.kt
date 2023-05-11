package com.example.notesmain.db

import android.provider.BaseColumns

object DbNameClass: BaseColumns{
    const val TABLE_NAME = "entry"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "subtitle"
    const val COLUMN_NAME_TIME = "time"

    const val DATABASE_VERSION = 8
    const val DATABASE_NAME = "NotesMain.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT," +
            "$COLUMN_NAME_CONTENT TEXT," +
            "$COLUMN_NAME_TIME TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}