package com.example.notesmain.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.icu.text.CaseMap.Title
import android.provider.BaseColumns
import android.util.Log
import kotlinx.coroutines.selects.select


class DbManager(context: Context) {
    val dbhelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbhelper.writableDatabase
    }
    fun insertToDb(title: String, content: String, time:String){
        val values = ContentValues().apply {
            put(DbNameClass.COLUMN_NAME_TITLE, title)
            put(DbNameClass.COLUMN_NAME_CONTENT, content)
            put(DbNameClass.COLUMN_NAME_TIME, time)
        }
        db?.insert(DbNameClass.TABLE_NAME,null, values)
    }

    fun updateItem(title: String, content: String, id:Int, time:String){
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(DbNameClass.COLUMN_NAME_TITLE, title)
            put(DbNameClass.COLUMN_NAME_CONTENT, content)
            put(DbNameClass.COLUMN_NAME_TIME, time)
        }
        db?.update(DbNameClass.TABLE_NAME, values,selection,null)
    }

    fun removeItemFromDb(id : String){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(DbNameClass.TABLE_NAME, selection,null)
    }

    @SuppressLint("Range")
    fun readDbData(searchText: String) : ArrayList<ListItem>{
        val dataList = ArrayList<ListItem>()
        val selection = "${DbNameClass.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(DbNameClass.TABLE_NAME, null, selection, arrayOf("%$searchText%"),null,null,null)

        while (cursor?.moveToNext()!!){
            val dataText = cursor.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_TITLE))
            val dataContext = cursor.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_CONTENT))
            val dataId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val time = cursor.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_TIME))
            var item = ListItem()
            item.title = dataText
            item.desc = dataContext
            item.id = dataId
            item.time = time
            dataList.add(item)
        }

        cursor.close()
        return dataList
    }

    fun closeDb(){
        dbhelper.close()
    }
}