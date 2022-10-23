package com.example.flashapp

import dialog.PopupMainDialog
import adapter.CollectionAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import storage.CollectionStorage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //findViewById<RecyclerView>(R.id.collection_list).adapter = CollectionAdapter()

        findViewById<Button>(R.id.add_button).setOnClickListener {
            PopupMainDialog(1).show(supportFragmentManager,null)
        }
    }
}