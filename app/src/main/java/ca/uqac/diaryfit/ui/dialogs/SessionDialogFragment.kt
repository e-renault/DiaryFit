package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.datas.Session
import ca.uqac.diaryfit.ui.adapters.EditSessionCardViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_SESSION = "sessionID"

class EditSessionDialogFragment : DialogFragment(ca.uqac.diaryfit.R.layout.dialog_edit_session) {
    //Fragment related elements
    private lateinit var fdialog: Dialog

    private var sessionID:Int = -1

    //ui elements
    private lateinit var recyclerView: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var returnBt: ImageButton
    private lateinit var saveBt: ImageButton
    private lateinit var title: EditText

    //rv elements
    private lateinit var exerciceAdapter: EditSessionCardViewAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        super.onCreateDialog(savedInstanceState)

        fdialog = Dialog(requireContext(), android.R.style.Theme_Material_Light_NoActionBar)
        fdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        fdialog.setCancelable(true)

        return fdialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            sessionID = it.getInt(ARG_SESSION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view == null) return null

        //TODO retrieve session to edit (or new session ?)
        val session:Session = MDatabase.getSession(sessionID)

        //inflate recyclerview
        recyclerView = view.findViewById(ca.uqac.diaryfit.R.id.editsession_rv_exercicelist) as RecyclerView
        exerciceAdapter = EditSessionCardViewAdapter(sessionID, this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        //TODO implement on click item

        //set Title
        title = view.findViewById(ca.uqac.diaryfit.R.id.editsession_et_sessionname) as EditText
        title.setText(session.getTitle())

        //add button
        addFAB = view.findViewById(ca.uqac.diaryfit.R.id.editsession_fab_add) as FloatingActionButton
        addFAB.setOnClickListener {
            addExercice()
        }

        //return btn
        returnBt = view.findViewById(ca.uqac.diaryfit.R.id.editsession_ib_close) as ImageButton
        returnBt.setOnClickListener {
            cancelDialog()
        }

        //save btn
        saveBt = view.findViewById(ca.uqac.diaryfit.R.id.editsession_ib_save) as ImageButton
        saveBt.setOnClickListener {
            saveDialog()
        }

        return view
    }

    companion object {
        const val TAG = "EditSessionFragment"
        @JvmStatic
        fun newInstance(sessionID:Int) =
            EditSessionDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SESSION, sessionID)
                }
            }
    }

    //add new exercice to session
    private fun addExercice() {
        //TODO retrieve last added exercice

        //ExerciceFragment.newInstance().show(childFragmentManager, ExerciceFragment.TAG)
        //TODO recover datas from addExercice fragment
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

    fun editExercice(ex:Exercice) {
        ExerciceFragment.editExercice(ex).show(childFragmentManager, ExerciceFragment.TAG)
    }
}