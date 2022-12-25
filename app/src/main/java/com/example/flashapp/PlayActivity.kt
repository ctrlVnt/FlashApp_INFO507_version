package com.example.flashapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import model.Cartes
import storage.CartesJSONFileStorage

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

        rejectButton.visibility = View.INVISIBLE
        confrimButton.visibility = View.INVISIBLE

        start()

    }

    private fun start() {
        if(i < storage.size() + 1) {
            rejectButton.visibility = View.INVISIBLE
            confrimButton.visibility = View.INVISIBLE
            if (storage.find(i)!!.image == Uri.EMPTY.toString()) {
                findViewById<TextView>(R.id.card_phrase).setText(storage.find(i)!!.question)
            }
            else{
                val selectedImage : Uri = Uri.parse(storage.find(i)!!.image)
                findViewById<ImageView>(R.id.question_img).setImageURI(selectedImage)
            }
            roundButton.setOnClickListener {
                findViewById<ImageView>(R.id.question_img).setImageURI(Uri.EMPTY)
                roundButton.visibility = View.INVISIBLE
                rejectButton.visibility = View.VISIBLE
                confrimButton.visibility = View.VISIBLE
                findViewById<TextView>(R.id.card_phrase).setText(storage.find(i)!!.reponse)
                confrimButton.setOnClickListener {
                    findViewById<ImageView>(R.id.question_img).setImageURI(Uri.EMPTY)
                    punteggio += 1
                    next()
                }
                rejectButton.setOnClickListener {
                    findViewById<ImageView>(R.id.question_img).setImageURI(Uri.EMPTY)
                    next()
                }
            }
        }else{
            findViewById<LinearLayout>(R.id.linearLayout_play).visibility = INVISIBLE
            val inflate = LayoutInflater.from(this)
            val item = inflate.inflate(R.layout.win_item, null)
            item.findViewById<TextView>(R.id.win_text).setText("Résultat: $punteggio")
            val win = AlertDialog.Builder(this)
            win.setView(item)
            win.setCancelable(false)
            win.setPositiveButton("Ok"){ dialog, _ ->
                dialog.dismiss()
                finish()
            }
            win.show()
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