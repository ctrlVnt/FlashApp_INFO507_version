package com.example.flashapp

import adapter.CollectionAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn: Button
    private lateinit var recv:RecyclerView
    private lateinit var collectionList:ArrayList<model.Collection>
    private lateinit var collectionAdapter:CollectionAdapter

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
        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }
        //findViewById<RecyclerView>(R.id.collection_list).adapter = CollectionAdapter()

        /*findViewById<Button>(R.id.add_button).setOnClickListener {
            PopupMainDialog(1).show(supportFragmentManager,null)
        }*/
    }

    private fun addInfo() {
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
            collectionList.add(model.Collection("Name: $names","Tag. : $tag", 0))
            collectionAdapter.notifyDataSetChanged()
            /*JSONObject().apply {
                put(
                    "collection",
                    JSONArray().apply {
                        put(JSONObject().apply {
                            put(names, tag)
                        })
                    }
                )
            }*/
            Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
}