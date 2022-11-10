package com.example.flashapp

import adapter.CollectionAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Collection
import request.Global
import storage.CartesJSONFileStorage
import storage.CollectionJSONFileStorage
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    /*come la define di C, definisci tipi globali non modificabili*/
    companion object {
        private const val LOCAL = "collection"
        private const val GLOBAL = "global"
    }

    /*inizializzi variabili che non sono di tipo comune, quindi Int, String etc... (init è sempre un inizializzazione)*/
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

    /*variabili globali normali*/
    private var i_local = 1
    private var i_global = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localList = ArrayList()

        addsBtn = findViewById(R.id.add_button)
        recvLocale = findViewById(R.id.collection_list)

        collectionAdapter = CollectionAdapter(this,localList, LOCAL)

        recvLocale.layoutManager = LinearLayoutManager(this)

        recvLocale.adapter = collectionAdapter


        globalList = ArrayList()
        recvGlobale = findViewById(R.id.global_list)
        globalAdapter = CollectionAdapter(this,globalList, GLOBAL)
        recvGlobale.layoutManager = LinearLayoutManager(this)
        recvGlobale.adapter = globalAdapter

        storageGlobal = CollectionJSONFileStorage(this, GLOBAL)

        global = Global(this, storageGlobal.size())

        /*Global Storage*/
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

        addsBtn.setOnClickListener {
            addInfo(storageLocal.size())
        }

        collectionAdapter.setonItemClickListener(object : CollectionAdapter.onItemClickListener{
            override fun onItemClick(nameItem: String, tagItem: String) {
                startCollectionActivity(findViewById(R.id.collection_item), nameItem, tagItem, LOCAL)
            }

            override fun onAddClick(position: Int) {
                /*val storageCart = CartesJSONFileStorage(this@MainActivity, storageLocal.find(position + 1)!!.name)
                for (f in 1 until storageCart.size()){
                    storageCart.delete(f)
                }*/
                if(storageLocal.size() > 1) {
                    for (k in position + 1 until storageLocal.size()) {
                        storageLocal.update(k, storageLocal.find(k + 1)!!)
                    }
                }
                storageLocal.delete(storageLocal.size())
                localList.removeAt(position)
                collectionAdapter.notifyDataSetChanged()
            }
        })

        globalAdapter.setonItemClickListener(object : CollectionAdapter.onItemClickListener{
            override fun onItemClick(nameItem: String, tagItem: String) {
                startCollectionActivity(findViewById(R.id.collection_item), nameItem, tagItem, GLOBAL)
            }

            override fun onAddClick(position: Int) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun addInfo(int: Int) {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.activity_popup_main,null)

        val collectionName = v.findViewById<EditText>(R.id.collect_name)
        val collectionTag = v.findViewById<EditText>(R.id.tag_name)
        val check = v.findViewById<CheckBox>(R.id.checkBox)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val names = collectionName.text.toString()
            val tag = collectionTag.text.toString()
            if(names != "" && tag != "") {
                localList.add(model.Collection(int, names, tag, 0))
                storageLocal.insert(
                    Collection(
                        int,
                        names,
                        tag,
                        0
                    )
                )
                /*metodo FINTO*/
                if(check.isChecked){
                    global.writeOnGlobal(Collection(
                        int,
                        names,
                        tag,
                        0
                    ))
                }
                collectionAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"Failed: content cannot be empty", Toast.LENGTH_SHORT).show()
            }
            /*spostare dismiss*/
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
            try {
                arraylist.add(
                    model.Collection(
                        storage.find(i)!!.id,
                        storage.find(i)!!.name,
                        storage.find(i)!!.tag,
                        storage.find(i)!!.card_number
                    )
                )
            }catch (e:java.lang.Exception){
                break
            }
        }
    }

    fun startCollectionActivity (view: View, name : String, tag : String, type: String) {
        val intent = Intent(this, CollectionActivity::class.java)

        intent.putExtra("collectionName", name)
        intent.putExtra("collectionTag", tag)
        intent.putExtra("collectiontype", type)
        startActivity(intent)
    }
}