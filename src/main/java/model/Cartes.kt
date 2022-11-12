package model

import android.net.Uri

class Cartes(
    val id: Int,
    val collection: String,
    val question: String,
    val reponse: String,
    val image: String
) {
    companion object {
        const val ID = "id"
        const val COLLECTION = "collection"
        const val QUESTION = "question"
        const val REPONSE = "reponse"
        const val IMAGE = "imagine_question"
    }
}