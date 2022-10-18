package model

class Card(
    val name: String,
    val tag: String,
    val card_number: Int
) {
    companion object {
        const val NAME = "name"
        const val TAG = "general"
        const val CARDNUMBER = "0"
    }
}