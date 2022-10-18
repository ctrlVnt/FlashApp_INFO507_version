package com.example.flashapp

import PopupMain
import adapter.CollectionAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addbutton = findViewById<Button>(R.id.add_button)

        findViewById<RecyclerView>(R.id.collection_list).adapter = CollectionAdapter()

        addbutton.setOnClickListener {
            var dialog = PopupMain()
            dialog.show(supportFragmentManager, "first popup")
        }

    }
}