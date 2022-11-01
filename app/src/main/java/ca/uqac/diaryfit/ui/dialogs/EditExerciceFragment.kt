package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ca.uqac.diaryfit.R

class EditExerciceFragment : DialogFragment(R.layout.dialog_edit_exercice) {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_edit_exercice)
        val yesBtn = dialog.findViewById(R.id.editexercice_bt_ok) as Button
        val noBtn = dialog.findViewById(R.id.editexercice_bt_cancel) as TextView
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        //dialog.show()

        return dialog
    }

    companion object {
        const val TAG = "EditExerciceFragment"
    }
}