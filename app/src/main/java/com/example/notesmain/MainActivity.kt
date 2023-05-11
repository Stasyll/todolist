package com.example.notesmain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmain.databinding.ActivityMainBinding
import com.example.notesmain.db.Adapter
import com.example.notesmain.db.DbManager

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding
    val dbManager = DbManager(this)
    val myAdapter = Adapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        init()
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
        fillAdapter()

    }

    override fun onDestroy() {
        dbManager.closeDb()
        super.onDestroy()
    }

    fun onClickNew(view: View){
        val i = Intent(this,EditActivity::class.java)
        startActivity(i)
    }

    fun init(){
        bindingClass.rcView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(bindingClass.rcView)
        bindingClass.rcView.adapter = myAdapter
    }

    private fun initSearchView(){
        bindingClass.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                val list = dbManager.readDbData(text!!)
                myAdapter.updateAdapter(list)
                return true
            }
        })
    }

    fun fillAdapter(){
        val list = dbManager.readDbData("")
        myAdapter.updateAdapter(list)
        if (list.size > 0){
            bindingClass.tvNoElements.visibility = View.GONE
        } else {
            bindingClass.tvNoElements.visibility = View.VISIBLE
        }
    }

    private fun getSwapMg():ItemTouchHelper{
        return ItemTouchHelper(object :ItemTouchHelper.
        SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               myAdapter.removeItem(viewHolder.adapterPosition,dbManager)
            }
        })
    }


}