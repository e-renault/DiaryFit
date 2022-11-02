package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.Exercice

class ExerciceFragment(private val ex: Exercice) : DialogFragment(R.layout.dialog_exercice) {
    //Fragment related elements
    private lateinit var fdialog: Dialog

    //UI
    private lateinit var okBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var editBtn : ImageButton

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        fdialog = Dialog(requireContext())
        fdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        fdialog.setCancelable(true)

        return fdialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view == null) return null

        okBtn = view.findViewById(R.id.exercice_bt_ok) as Button
        okBtn.setOnClickListener {
            saveDialog()
        }

        cancelBtn = view.findViewById(R.id.exercice_bt_cancel) as Button
        cancelBtn.setOnClickListener {
            cancelDialog()
        }

        editBtn = view.findViewById(R.id.exercice_ib_exercice) as ImageButton
        editBtn.setOnClickListener {
            editExercice()
        }

        return view
    }

    companion object {
        const val TAG = "ExerciceFragment"
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

    //edit current exercice
    private fun editExercice() {
        EditExerciceFragment().show(childFragmentManager, EditExerciceFragment.TAG)
    }
}