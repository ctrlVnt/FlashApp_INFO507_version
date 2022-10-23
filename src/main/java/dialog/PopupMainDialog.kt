package dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.flashapp.R

class PopupMainDialog(private val id: Int?) : DialogFragment(){

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val ok = requireActivity().layoutInflater.inflate(R.layout.activity_popup_main, null)
        ok.findViewById<Button>(R.id.ok_add_collection).setOnClickListener(){
            Toast.makeText(context, "Coucou !", Toast.LENGTH_SHORT).show()
        }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }*/
}