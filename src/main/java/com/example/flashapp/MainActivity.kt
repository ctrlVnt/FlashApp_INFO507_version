package com.example.flashapp

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
import model.Collection
import request.Global
import storage.CollectionJSONFileStorage
import storage.CollectionStorage
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        private const val LOCAL = "collection"
        private const val GLOBAL = "global"
    }

    private lateinit var addsBtn: Button
    private lateinit var recvLocale:RecyclerView
    private lateinit var localList:ArrayList<model.Collection>
    private lateinit var collectionAdapter:CollectionAdapter

    private lateinit var storageGlobal: CollectionJSONFileStorage
    private lateinit var storageLocal: CollectionJSONFileStorage

    private lateinit var global: Global

    private lateinit var globalAdapter: CollectionAdapter
    private lateinit var globalList: ArrayList<model.Collection>
    private lateinit var recvGlobale: RecyclerView

    private var i_local = 1
    private var i_global = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localList = ArrayList()

        addsBtn = findViewById(R.id.add_button)
        recvLocale = findViewById(R.id.collection_list)

        collectionAdapter = CollectionAdapter(this,localList)

        recvLocale.layoutManager = LinearLayoutManager(this)

        recvLocale.adapter = collectionAdapter


        globalList = ArrayList()
        recvGlobale = findViewById(R.id.global_list)
        globalAdapter = CollectionAdapter(this,globalList)
        recvGlobale.layoutManager = LinearLayoutManager(this)
        recvGlobale.adapter = globalAdapter

        global = Global(this)

        /*Global Storage, funziona ma va sistemato*/
        storageGlobal = CollectionJSONFileStorage(this, GLOBAL)
        if(i_global < storageGlobal.size()) {
            loadJson(storageGlobal, globalList, i_global)
            i_global = storageGlobal.size()
        }

        /*Local storage*/
        storageLocal = CollectionJSONFileStorage(this, LOCAL)
        if(i_local <= storageLocal.size()) {
            loadJson(storageLocal, localList, i_local)
            i_local = storageLocal.size()
        }

        var i = 0
        addsBtn.setOnClickListener {
            addInfo(i)
            i = i + 1
        }

        collectionAdapter.setonItemClickListener(object : CollectionAdapter.onItemClickListener{
            override fun onItemClick(nameItem: String, tagItem: String) {
                startCollectionActivity(findViewById(R.id.collection_item), nameItem, tagItem)
            }
        })

        globalAdapter.setonItemClickListener(object : CollectionAdapter.onItemClickListener{
            override fun onItemClick(nameItem: String, tagItem: String) {
                startCollectionActivity(findViewById(R.id.collection_item), nameItem, tagItem)
            }
        })
    }

    private fun addInfo(int: Int) {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.activity_popup_main,null)

        val collectionName = v.findViewById<EditText>(R.id.collect_name)
        val collectionTag = v.findViewById<EditText>(R.id.tag_name)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = collectionName.text.toString()
            val tag = collectionTag.text.toString()
            if(names != "" && tag != "") {
                localList.add(model.Collection(int, "Name: $names", "Tag. : $tag", 0))
                storageLocal.insert(
                    Collection(
                        int,
                        names,
                        tag,
                        0
                    )
                )
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

    private fun loadJson(storage: CollectionJSONFileStorage, arraylist:ArrayList<model.Collection>, fine:Int) {
        println(storage.size())
        for (i in fine until storage.size() + 1) {
            arraylist.add(
                model.Collection(
                    0,
                    "Name: ${storage.find(i)!!.name}",
                    "Tag. : ${storage.find(i)!!.tag}",
                    0
                )
            )
        }
    }

    fun startCollectionActivity (view: View, name : String, tag : String) {
        val intent = Intent(this, CollectionActivity::class.java)

        intent.putExtra("collectionName", name)
        intent.putExtra("collectionTag", tag)
        startActivity(intent)
    }
}