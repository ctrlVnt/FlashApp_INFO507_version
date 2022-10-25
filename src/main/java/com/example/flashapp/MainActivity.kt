package com.example.flashapp

import GlobalAdapter
import adapter.CollectionAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import request.Global
import storage.CollectionJSONFileStorage
import storage.CollectionStorage

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn: Button
    private lateinit var recv:RecyclerView
    private lateinit var collectionList:ArrayList<model.Collection>
    private lateinit var collectionAdapter:CollectionAdapter

    private lateinit var storage: CollectionJSONFileStorage

    private lateinit var recvg:RecyclerView
    private lateinit var globalAdapter:GlobalAdapter
    private lateinit var globalList:ArrayList<model.Collection>
    private lateinit var pars:Global

    private lateinit var store:CollectionStorage

    /*lateinit var list: RecyclerView

    companion object {
        const val EXTRA_COLLECTION = "EXTRA_COLLECTION"
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**set List*/
        collectionList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.add_button)
        recv = findViewById(R.id.collection_list)
        /**set Adapter*/
        collectionAdapter = CollectionAdapter(this,collectionList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = collectionAdapter

        storage = CollectionJSONFileStorage(this)

        collectionAdapter.setonItemClickListener(object : CollectionAdapter.onItemClickListener{
            override fun onItemClick(nameItem: String) {
                startCollectionActivity(findViewById(R.id.collection_item), nameItem)
            }

        })

        /**set Global*/
        pars = Global(this)
        /*recvg = findViewById(R.id.global_list)
        globalList = ArrayList()
        globalAdapter = GlobalAdapter(this, globalList)
        recvg.layoutManager = LinearLayoutManager(this)
        recvg.adapter = globalAdapter*/
        /**set Storage*/
        //store.get(this)


        /**set Dialog*/
        var i :Int = 0
        addsBtn.setOnClickListener {
            addInfo(i)
            i = i + 1
        }


        //findViewById<RecyclerView>(R.id.collection_list).adapter = CollectionAdapter()

        /*findViewById<Button>(R.id.add_button).setOnClickListener {
            PopupMainDialog(1).show(supportFragmentManager,null)
        }*/
        /*
        list = findViewById<RecyclerView>(R.id.collection_list)
        list.adapter = object : CollectionAdapter(applicationContext, collectionList) {
            override fun onItemClick(view: View) {
                val intent = Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra(EXTRA_COLLECTION, view.tag as Int)
                }
                startActivity(intent)
            }

            override fun onLongItemClick(view: View): Boolean {
                Toast.makeText(applicationContext, "Je veux supprimer", Toast.LENGTH_SHORT).show()
                return true
            }

        }*/
    }

    private fun addInfo(int: Int) {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.activity_popup_main,null)
        /**set view*/
        val collectionName = v.findViewById<EditText>(R.id.collect_name)
        val collectionTag = v.findViewById<EditText>(R.id.tag_name)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = collectionName.text.toString()
            val tag = collectionTag.text.toString()
            if(names != "" && tag != "") {
                collectionList.add(model.Collection(int, "Name: $names", "Tag. : $tag", 0))
                /**inserimento nel json*/
                storage.insert(model.Collection(int, "Name: $names", "Tag. : $tag", 0))
                collectionAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"Failed: content cannot be empty", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()

        }
        addDialog.create()
        addDialog.show()
    }

    fun startCollectionActivity (view: View, value : String) {
        val intent = Intent(this, CollectionActivity::class.java)
        // To pass any data to next activity
        intent.putExtra("collectionName", value)
        startActivity(intent)
    }
}