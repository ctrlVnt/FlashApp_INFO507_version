package com.example.flashapp

import adapter.CartesAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Cartes
import model.Collection
import storage.CartesJSONFileStorage
import storage.CollectionJSONFileStorage

class CollectionActivity : AppCompatActivity() {

    private lateinit var addsBtn: Button
    private lateinit var recv: RecyclerView
    private lateinit var cartesList: ArrayList<Cartes>
    private lateinit var cartesAdapter: CartesAdapter

    private lateinit var storageCart: CartesJSONFileStorage

    private lateinit var galleryActivityLauncher:
            ActivityResultLauncher<Intent>

    private var i_local = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_collection)

        findViewById<TextView>(R.id.collection_name).setText(intent.getStringExtra("collectionName"))
        findViewById<TextView>(R.id.tag).setText(intent.getStringExtra("collectionTag"))

        var nameCollection = intent.getStringExtra("collectionName")!!

        cartesList = ArrayList()

        addsBtn = findViewById(R.id.add_button)

        recv = findViewById(R.id.cartes_list)

        cartesAdapter = CartesAdapter(this, cartesList)
        recv.layoutManager = GridLayoutManager(this,3,RecyclerView.VERTICAL,false)
        recv.adapter = cartesAdapter

        galleryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val uri: Uri? = data?.data
                val imageView = findViewById<ImageView>(R.id.image_question)
                imageView.setImageURI(uri)
            }
        }

        storageCart = CartesJSONFileStorage(this, nameCollection)
        if(i_local <= storageCart.size()) {
            loadJson(storageCart, cartesList, i_local)
            i_local = storageCart.size()
        }

        addsBtn.setOnClickListener {
            addCard()
        }

        findViewById<Button>(R.id.play_button).setOnClickListener{
            startPlayActivity(nameCollection)
        }

        cartesAdapter.setonItemClickListener(object : CartesAdapter.onItemClickListener{
            override fun onItemClick(questionItem: String, responseItem: String) {
                editCard(questionItem, responseItem)
            }
        })
    }

    private fun addCard() {
        val inflter = LayoutInflater.from(this)
        val item = inflter.inflate(R.layout.layout_add_edit_card, null)

        val cardQuestion = item.findViewById<EditText>(R.id.edit_question)
        val cardAnswer = item.findViewById<EditText>(R.id.edit_answer)

        val addDialog = AlertDialog.Builder(this)
            .setView(item)
            .setPositiveButton("Ok") {
                dialog, _ ->
            val question = cardQuestion.text.toString()
            val response = cardAnswer.text.toString()
            cartesList.add(Cartes(0, "collection", "Question : $question", "Response : $response"))
            storageCart.insert(
                Cartes(
                    0,
                    "collection",
                    question,
                    response
                )
            )
            cartesAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            .show()

        addDialog.findViewById<FloatingActionButton>(R.id.button_image)!!.setOnClickListener {
            if (checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                startActivity(intent)
            }
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type =  "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            galleryActivityLauncher.launch(intent)
        }

        addDialog.setOnDismissListener {

        }
    }

    private fun editCard(questionItem: String, responseItem: String) {
        val inflter = LayoutInflater.from(this)
        val item = inflter.inflate(R.layout.layout_add_edit_card, null)

        val cardQuestion = item.findViewById<EditText>(R.id.edit_question)
        val cardAnswer = item.findViewById<EditText>(R.id.edit_answer)

        val addDialog = AlertDialog.Builder(this)
            .setView(item)
            .setPositiveButton("Ok") {
                    dialog, _ ->
                val question = cardQuestion.text.toString()
                val response = cardAnswer.text.toString()
                cartesList[0] = Cartes(0, "collection", "Question : $question", "Response : $response")
                storageCart.update(0,
                    Cartes(
                        0,
                        "collection",
                        question,
                        response
                    )
                )
                cartesAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            .show()

        addDialog.findViewById<FloatingActionButton>(R.id.button_image)!!.setOnClickListener {
            if (checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                startActivity(intent)
            }
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type =  "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            galleryActivityLauncher.launch(intent)
        }

        addDialog.setOnDismissListener {

        }
    }

    private fun loadJson(storage: CartesJSONFileStorage, arraylist:ArrayList<Cartes>, fine:Int) {
        println(storage.size())
        for (i in fine until storage.size() + 1) {
            arraylist.add(
                Cartes(
                    0,
                    "collection: ${storage.find(i)!!.collection}",
                    "Question : ${storage.find(i)!!.question}",
                    "Response : ${storage.find(i)!!.reponse}"
                )
            )
        }
    }

    private fun checkPermission(permission: String): Boolean {
        var res = true
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 0)
            }
            res = false
        }
        return res
    }

    private fun startPlayActivity (list: String) {
        val intent = Intent(this, PlayActivity::class.java)

        intent.putExtra("list", list)
        startActivity(intent)
    }
}