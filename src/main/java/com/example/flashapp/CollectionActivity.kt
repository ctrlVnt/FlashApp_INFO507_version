package com.example.flashapp

import adapter.CartesAdapter
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Cartes
import storage.CartesJSONFileStorage

class CollectionActivity : AppCompatActivity() {

    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var cartesList: ArrayList<Cartes>
    private lateinit var cartesAdapter: CartesAdapter

    private var isReadPermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var storageCart: CartesJSONFileStorage

    private lateinit var galleryActivityLauncher: ActivityResultLauncher<Intent>
    //lateinit var uploadphoto: ActivityResultLauncher<Intent>

    private lateinit var uri: Uri

    private var i_local = 1

    private lateinit var nameCollection: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_collection)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted

        }

        val type = intent.getStringExtra("collectiontype")

        var glob = 0

        if(type == "global")
            glob = 1

        addsBtn = findViewById(R.id.add_button)

        if (type == "global"){
            addsBtn.visibility = View.INVISIBLE
        }

        findViewById<TextView>(R.id.collection_name).setText(intent.getStringExtra("collectionName"))
        findViewById<TextView>(R.id.tag).setText(intent.getStringExtra("collectionTag"))

        nameCollection = intent.getStringExtra("collectionName")!!

        cartesList = ArrayList()

        recv = findViewById(R.id.cartes_list)

        cartesAdapter = CartesAdapter(this, cartesList, glob)
        recv.layoutManager = GridLayoutManager(this,3,RecyclerView.VERTICAL,false)
        recv.adapter = cartesAdapter

        uri = Uri.EMPTY
        galleryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data?.data!!
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }

        storageCart = CartesJSONFileStorage(this, nameCollection)
        if(i_local <= storageCart.size()) {
            loadJson(storageCart, cartesList, i_local)
            i_local = storageCart.size()
        }
        findViewById<TextView>(R.id.nCartes).setText("NOMBRE DE CARTES: " + storageCart.size().toString())

        addsBtn.setOnClickListener {
            addCard(storageCart.size())
        }

        findViewById<Button>(R.id.play_button).setOnClickListener{
            if(storageCart.size() == 0){
                val infltererror = LayoutInflater.from(this)
                val itemerror = infltererror.inflate(R.layout.play_error, null)
                val error = AlertDialog.Builder(this)
                error.setView(itemerror)
                error.setPositiveButton("Ok"){ dialog, _ ->
                    dialog.dismiss()
                }
                error.show()
            }else {
                startPlayActivity(nameCollection)
            }
        }

        cartesAdapter.setonItemClickListener(object : CartesAdapter.onItemClickListener{
            override fun onItemClick(questionItem: String, responseItem: String, position: Int) {
                editCard(questionItem, responseItem, position)
            }

            override fun deleteCardClick(position: Int) {
                for (k in position + 1 until storageCart.size() ) {
                    storageCart.update(k, storageCart.find(k + 1)!!)
                }
                findViewById<TextView>(R.id.nCartes).setText("NOMBRE DE CARTES: " + (storageCart.size()-1).toString())
                storageCart.delete(storageCart.size())
                cartesList.removeAt(position)
                cartesAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun addCard(count:Int) {
        val inflter = LayoutInflater.from(this)
        val item = inflter.inflate(R.layout.layout_add_edit_card, null)
        checkPermission()

        val cardQuestion = item.findViewById<EditText>(R.id.edit_question)
        val cardAnswer = item.findViewById<EditText>(R.id.edit_answer)


        val addDialog = AlertDialog.Builder(this)
            .setView(item)
            .setPositiveButton("Ok") {
                dialog, _ ->
                val question = cardQuestion.text.toString()
                val response = cardAnswer.text.toString()
                if(response != "" || (question != "" && cardQuestion.visibility != INVISIBLE)) {
                    cartesList.add(Cartes(count, nameCollection, question, response, uri.toString()))
                    storageCart.insert(
                        Cartes(
                            count,
                            nameCollection,
                            question,
                            response,
                            uri.toString()
                        )
                    )
                    cartesAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                }else{
                    Toast.makeText(this,"Failed: content cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            }
            .show()

        addDialog.findViewById<FloatingActionButton>(R.id.button_image)!!.setOnClickListener {
            if (isReadPermissionGranted) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                cardQuestion.setText("")
                cardQuestion.visibility = INVISIBLE
                galleryActivityLauncher.launch(intent)
                item.findViewById<ImageView>(R.id.image_view_question).setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_done_all_24))
            }
        }

        addDialog.setOnDismissListener {
            uri = Uri.EMPTY
            findViewById<TextView>(R.id.nCartes).setText("NOMBRE DE CARTES: " + storageCart.size().toString())
        }
    }

    private fun editCard(questionItem: String, responseItem: String, position: Int) {
        val inflter = LayoutInflater.from(this)
        val item = inflter.inflate(R.layout.layout_add_edit_card, null)
        checkPermission()

        val cardQuestion = item.findViewById<EditText>(R.id.edit_question)
        val cardAnswer = item.findViewById<EditText>(R.id.edit_answer)

        cardQuestion.setText(questionItem)
        cardAnswer.setText(responseItem)

        if(storageCart.find(position + 1)!!.image != Uri.EMPTY.toString()){
            cardQuestion.visibility = INVISIBLE
            item.findViewById<ImageView>(R.id.image_view_question).setImageURI(Uri.parse(storageCart.find(position+1)!!.image))
        }

        val addDialog = AlertDialog.Builder(this)
            .setView(item)
            .setPositiveButton("Ok") {
                    dialog, _ ->
                val question = cardQuestion.text.toString()
                val response = cardAnswer.text.toString()
                cartesList[position] =
                    Cartes(position, nameCollection, question, response, uri.toString())
                storageCart.update(
                    position + 1,
                    Cartes(
                        position,
                        nameCollection,
                        question,
                        response,
                        uri.toString()
                    )
                )
                cartesAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        addDialog.findViewById<FloatingActionButton>(R.id.button_image)!!.setOnClickListener {
            if (isReadPermissionGranted) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                galleryActivityLauncher.launch(intent)

            }
        }

        addDialog.setOnDismissListener {
            uri = Uri.EMPTY
        }
    }

    private fun loadJson(storage: CartesJSONFileStorage, arraylist:ArrayList<Cartes>, fine:Int) {
        for (i in fine until storage.size() + 1) {
            arraylist.add(
                Cartes(
                    storage.find(i)!!.id,
                    "collection: ${storage.find(i)!!.collection}",
                    storage.find(i)!!.question,
                    storage.find(i)!!.reponse,
                    storage.find(i)!!.image
                )
            )
        }
    }

    private fun checkPermission(){
        val isReadPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        isReadPermissionGranted = isReadPermission

        val permissionRequest = mutableListOf<String>()
        if (!isReadPermissionGranted){
            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }

    private fun startPlayActivity (list: String) {
        val intent = Intent(this, PlayActivity::class.java)

        intent.putExtra("list", list)
        startActivity(intent)
    }
}