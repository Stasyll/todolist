package com.example.notesmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.notesmain.databinding.ActivityEditBinding
import com.example.notesmain.db.DbManager
import com.example.notesmain.db.IntentConstants
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    val dbManager = DbManager(this)
    var isEditState = false
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMyIntens()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()

    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    fun onClickSave(view: View) {
        val mytitle = binding.edTitle.text.toString()
        val mydesc = binding.edDesc.text.toString()

        if (mytitle != "") {
            if (isEditState) {
                dbManager.updateItem(mytitle, mydesc, id,getCurrentTime())
            } else {
                dbManager.insertToDb(mytitle, mydesc,getCurrentTime())
            }
            finish()
        }
    }


    fun getMyIntens() {
        val i = intent

        if (i != null) {
            if (i.getStringExtra(IntentConstants.I_TITLE_KEY) != null) {
                isEditState = true
                binding.edTitle.setText(i.getStringExtra(IntentConstants.I_TITLE_KEY))
                binding.edDesc.setText(i.getStringExtra(IntentConstants.I_DESC_KEY))
                id = i.getIntExtra(IntentConstants.I_ID_KEY, 0)
            }
        }
    }

    private fun getCurrentTime():String{
        val time = Calendar.getInstance().time
        val formater = SimpleDateFormat("dd-MM-yy hh:mm", Locale.getDefault())
        return formater.format(time)
    }
}