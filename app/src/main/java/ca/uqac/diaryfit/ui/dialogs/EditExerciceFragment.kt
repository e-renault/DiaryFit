package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ca.uqac.diaryfit.R

class EditExerciceFragment() : DialogFragment(R.layout.dialog_new_exercice) {
    //Fragment related elements
    private lateinit var fdialog: Dialog

    //UI
    private lateinit var yesBtn : Button
    private lateinit var noBtn : TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        fdialog = Dialog(requireContext())
        fdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        fdialog.setCancelable(true)
        fdialog.setContentView(R.layout.dialog_new_exercice)

        return fdialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view == null) return null

        yesBtn = view.findViewById(R.id.newexercice_bt_ok) as Button
        yesBtn.setOnClickListener {
            saveDialog()
        }
        noBtn = view.findViewById(R.id.newexercice_bt_cancel) as TextView
        noBtn.setOnClickListener {
            cancelDialog()
        }

        return view
    }

    companion object {
        const val TAG = "EditExerciceFragment"
    }

    //cancel fragment & opération (recover previous datas)
    private fun cancelDialog() {
        fdialog.dismiss()
    }

    //return or save state and terminate fragment
    private fun saveDialog() {
        //TODO return or save state and terminate fragment
        fdialog.dismiss()
    }
}