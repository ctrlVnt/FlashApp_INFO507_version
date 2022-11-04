package com.example.flashapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import model.Cartes
import storage.CartesJSONFileStorage
import storage.CollectionStorage

class PlayActivity: AppCompatActivity() {

    private lateinit var listCard: ArrayList<Cartes>
    private lateinit var storage: CartesJSONFileStorage
    private lateinit var rejectButton: Button
    private lateinit var confrimButton: Button
    private lateinit var roundButton: Button
    private var punteggio = 0
    private var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_play)

        var nameList = intent.getStringExtra("list")!!

        findViewById<TextView>(R.id.collection_text).setText(nameList)

        storage = CartesJSONFileStorage(this, nameList)

        rejectButton = findViewById(R.id.reject)
        confrimButton = findViewById(R.id.confirm)
        roundButton = findViewById(R.id.round)

        start()

    }

    private fun start() {
        if(i < storage.size() + 1) {
            rejectButton.visibility = View.INVISIBLE
            confrimButton.visibility = View.INVISIBLE
            findViewById<TextView>(R.id.card_phrase).setText(storage.find(i)!!.question)
            roundButton.setOnClickListener {
                roundButton.visibility = View.INVISIBLE
                rejectButton.visibility = View.VISIBLE
                confrimButton.visibility = View.VISIBLE
                findViewById<TextView>(R.id.card_phrase).setText(storage.find(i)!!.reponse)
                confrimButton.setOnClickListener {
                    punteggio += 1
                    next()
                }
                rejectButton.setOnClickListener {
                    next()
                }
            }
        }else{
            roundButton.setText("OK")
            findViewById<TextView>(R.id.card_phrase).setText("HAI FATTO: $punteggio")
            roundButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun next(){
        rejectButton.visibility = View.INVISIBLE
        confrimButton.visibility = View.INVISIBLE
        roundButton.visibility = View.VISIBLE
        i +=1
        start()
    }

}