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
import org.json.JSONObject
import request.Global
import storage.CollectionJSONFileStorage
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn: Button
    private lateinit var recvLocale:RecyclerView
    private lateinit var collectionList:ArrayList<model.Collection>
    private lateinit var collectionAdapter:CollectionAdapter

    private lateinit var storageLocal: CollectionJSONFileStorage

    private lateinit var storageGlobal: Global

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collectionList = ArrayList()

        addsBtn = findViewById(R.id.add_button)
        recvLocale = findViewById(R.id.collection_list)

        collectionAdapter = CollectionAdapter(this,collectionList)

        recvLocale.layoutManager = LinearLayoutManager(this)

        storageLocal = CollectionJSONFileStorage(this)
        /*var obj = JSONObject(loadJSon())
        storageLocal.insert(model.Collection(0, "Name: scimmia", "Tag. : io", 0))
        storageLocal.insert(model.Collection(1, "Name: AHAHAH", "Tag. : GGGGG", 0))
        //storageLocal.tieni()

        var jsonArray = obj.getJSONArray("collection")
        for (i in 0 until jsonArray.length()) {
            val collectionDetail = jsonArray.getJSONObject(i)
            collectionList.add(model.Collection(1, "Name: xxxx", "Tag. : xxxx", 4))
        }*/
        recvLocale.adapter = collectionAdapter
        //loadCollections(storageLocal.find(0))

        collectionAdapter.setonItemClickListener(object : CollectionAdapter.onItemClickListener{
            override fun onItemClick(nameItem: String, tagItem: String) {
                startCollectionActivity(findViewById(R.id.collection_item), nameItem, tagItem)
            }
        })

        //storageGlobal = Global(this)

        var i :Int = 0
        addsBtn.setOnClickListener {
            addInfo(i)
            i = i + 1
        }
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
                collectionList.add(model.Collection(int, "Name: $names", "Tag. : $tag", 0))

                /**inserimento nel json*/
                storageLocal.insert(model.Collection(int, "Name: $names", "Tag. : $tag", 0))

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

    private fun loadCollections(find: Collection?) {
        if (find != null) {
            collectionList.add(model.Collection(find.id, "Name: ${find.name}", "Tag. : ${find.tag}", find.card_number))
        }else{
            Toast.makeText(this,"NON C'È NULLA", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadJSon(): String? {
        val json: String?
        try {
            val inputStream = assets.open("storage_collection.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    fun startCollectionActivity (view: View, name : String, tag : String) {
        val intent = Intent(this, CollectionActivity::class.java)
        // To pass any data to next activity
        intent.putExtra("collectionName", name)
        intent.putExtra("collectionTag", tag)
        startActivity(intent)
    }
}