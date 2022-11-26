package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.ui.adapters.EditSessionCardViewAdapter
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.MWeigth
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.datas.exercices.Exercice
import ca.uqac.diaryfit.datas.exercices.ExerciceRepetition
import com.google.android.material.floatingactionbutton.FloatingActionButton


private const val ARG_SESSION = "session_obj"
private const val ARG_RET_TYPE = "session_ret_type"
const val ARG_SESSION_DIALOG_RET = "Session_dialog_return"
const val ARG_SESSION_EDIT = "Session_dialog_return_edit"
const val ARG_SESSION_NEW = "Session_dialog_return_new"
private const val ADD = -1

class EditSessionDialogFragment :
    DialogFragment(ca.uqac.diaryfit.R.layout.dialog_edit_session),
    EditSessionCardViewAdapter.ExerciceEditListener {
    //Fragment related elements
    private lateinit var fdialog: Dialog

    private var exerciceID:Int = ADD
    private var retARG:String = "error"

    //ui elements
    private lateinit var recyclerView: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var returnBt: ImageButton
    private lateinit var saveBt: ImageButton
    private lateinit var title: EditText

    //rv elements
    private var session = Session()
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
            session = it.getParcelable(ARG_SESSION)!!
            retARG = it.getString(ARG_RET_TYPE).toString()
        }

        childFragmentManager.setFragmentResultListener("ExerciceDialogReturn", this) {
                requestKey, bundle ->
            val result = bundle.getParcelable<Exercice>("Exercice")

            if (result != null) {
                if (exerciceID == ADD) {
                    session.add(result)
                    //Toast.makeText(context, "Added!", Toast.LENGTH_SHORT)
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    session.set(exerciceID, result)
                    //Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view == null) return null

        //inflate recyclerview
        recyclerView = view.findViewById(ca.uqac.diaryfit.R.id.editsession_rv_exercicelist) as RecyclerView
        exerciceAdapter = EditSessionCardViewAdapter(session.getExerciceList2(), this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        //set Title
        title = view.findViewById(ca.uqac.diaryfit.R.id.editsession_et_sessionname) as EditText
        title.setText(session.getTitle())
        title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {session.setName(s.toString())}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        //add button
        addFAB = view.findViewById(ca.uqac.diaryfit.R.id.editsession_fab_add) as FloatingActionButton
        addFAB.setOnClickListener {
            addExercice()
        }

        //return btn
        returnBt = view.findViewById(ca.uqac.diaryfit.R.id.editsession_ib_close) as ImageButton
        returnBt.setOnClickListener {
            fdialog.dismiss()
        }

        //save btn
        saveBt = view.findViewById(ca.uqac.diaryfit.R.id.editsession_ib_save) as ImageButton
        saveBt.setOnClickListener {
            setFragmentResult(ARG_SESSION_DIALOG_RET, bundleOf(retARG to session))
            fdialog.dismiss()
        }

        return view
    }

    //add new exercice to session
    private fun addExercice() {
        //TODO retrieve last added exercice
        val ex = ExerciceRepetition(-1,1,1, MWeigth(0.0F), MTime(0,0,0))
        ExerciceFragment.editExercice(ex).show(childFragmentManager, ExerciceFragment.TAG)
        exerciceID = ADD
    }

    override fun onClickOnCardview(_exID: Int) {
        val ex: Exercice = session.get(_exID)
        ExerciceFragment.editExercice(ex).show(childFragmentManager, ExerciceFragment.TAG)
        exerciceID = _exID
    }

    companion object {
        const val TAG = "EditSessionFragment"
        @JvmStatic
        fun editSessionInstance(session: Session, rettype:String) =
            EditSessionDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SESSION, session)
                    putString(ARG_RET_TYPE, rettype)
                }
            }
    }
}