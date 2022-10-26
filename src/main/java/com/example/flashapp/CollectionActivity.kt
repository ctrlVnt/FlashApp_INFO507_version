package com.example.flashapp

import adapter.CartesAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model.Cartes

class CollectionActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var buttonimg: Button

    private lateinit var addsBtn: Button
    private lateinit var recv: RecyclerView
    private lateinit var cartesList: ArrayList<Cartes>
    private lateinit var cartesAdapter: CartesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_collection)

        findViewById<TextView>(R.id.collection_name).setText(intent.getStringExtra("collectionName"))
        findViewById<TextView>(R.id.tag).setText(intent.getStringExtra("collectionTag"))

        cartesList = ArrayList()

        addsBtn = findViewById(R.id.play_button)

        recv = findViewById(R.id.cartes_list)

        cartesAdapter = CartesAdapter(this, cartesList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = cartesAdapter


        addsBtn.setOnClickListener {
            addCard()
        }

        //imageView = findViewById(R.id.image_question)
        /*buttonimg = findViewById(R.id.play_button)

        val galleryActivityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val uri: Uri? = data?.data
                    val imageView = findViewById<ImageView>(R.id.image_question)
                    imageView.setImageURI(uri)
                }
            }

        buttonimg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type =  "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            galleryActivityLauncher.launch(intent)
        }*/*/
    }

    private fun addCard() {
        val inflter = LayoutInflater.from(this)
        val item = inflter.inflate(R.layout.layout_add_edit_card, null)

        val cardQuestion = item.findViewById<EditText>(R.id.edit_question)
        val cardAnswer = item.findViewById<EditText>(R.id.edit_answer)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(item)

        addDialog.setPositiveButton("Ok") {
                dialog, _ ->
            val question = cardQuestion.text.toString()
            val response = cardAnswer.text.toString()
            cartesList.add(Cartes(0, "collection", "Question : $question", "Response : $response"))
            cartesAdapter.notifyDataSetChanged()

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
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

}