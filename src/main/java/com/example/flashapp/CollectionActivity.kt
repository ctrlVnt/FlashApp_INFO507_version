package com.example.flashapp

import adapter.CartesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model.Cartes

class CollectionActivity : AppCompatActivity() {

    private lateinit var addsBtn: Button
    private lateinit var recv: RecyclerView
    private lateinit var cartesList:ArrayList<Cartes>
    private lateinit var cartesAdapter: CartesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_collection)

        findViewById<TextView>(R.id.collection_name).setText(intent.getStringExtra("collectionName"))
        findViewById<TextView>(R.id.tag).setText(intent.getStringExtra("collectionTag"))

        /**set List*/
        cartesList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.play_button)
        recv = findViewById<RecyclerView>(R.id.cartes_list)
        /**set Adapter*/
        cartesAdapter = CartesAdapter(this,cartesList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = cartesAdapter


        /**set Dialog*/
        addsBtn.setOnClickListener {
            addCard()
        }
    }

    private fun addCard() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.layout_add_edit_card,null)

        val collectionName = v.findViewById<EditText>(R.id.edit_question)
        val collectionTag = v.findViewById<EditText>(R.id.edit_answer)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val question = collectionName.text.toString()
            val response = collectionTag.text.toString()
            cartesList.add(Cartes(0, "collection", "Question : $question","Response : $response"))
            cartesAdapter.notifyDataSetChanged()

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