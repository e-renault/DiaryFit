package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentMainBinding
import ca.uqac.diaryfit.ui.adapters.ExerciceCardViewAdapter
import ca.uqac.diaryfit.ui.adapters.TodaySessionCardViewAdapter
import ca.uqac.diaryfit.datas.MDatabase
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.dialogs.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(),
    ExerciceCardViewAdapter.ExerciceEditListener,
    TodaySessionCardViewAdapter.SessionEditListener{
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //UI
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciceAdapter: TodaySessionCardViewAdapter
    private lateinit var addExercice: FloatingActionButton

    private var exID:Int = -1
    private var sessID:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener("ExerciceDialogReturn", this) {
                requestKey, bundle ->
            val result = bundle.getParcelable<Exercice>("Exercice")

            if (result != null) {
                MDatabase.setExercice(sessID, exID, result)
                Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        childFragmentManager.setFragmentResultListener(ARG_SESSION_DIALOG_RET, this) {
                requestKey, bundle ->
            val edit = bundle.getParcelable<Session>(ARG_SESSION_EDIT)
            val new = bundle.getParcelable<Session>(ARG_SESSION_NEW)

            if (edit != null) {
                MDatabase.setSession(sessID, edit)
                Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT)
                recyclerView.adapter?.notifyDataSetChanged()
            }

            if (new != null) {
                MDatabase.addSession(new)
                Toast.makeText(context, "New!", Toast.LENGTH_SHORT)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.frgmain_rv) as RecyclerView
        exerciceAdapter = TodaySessionCardViewAdapter(MDatabase.getTodaySessions(), this, this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        addExercice = root.findViewById(R.id.frgmain_fab_add)
        addExercice.setOnClickListener {
            EditSessionDialogFragment.editSessionInstance(Session(), ARG_SESSION_NEW)
                .show(childFragmentManager, EditSessionDialogFragment.TAG)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickOnCardview(_exID: Int, _sessID: Int) {
        val ex: Exercice? = MDatabase.getExercice(_sessID, _exID)
        if (ex != null) ExerciceFragment.editExercice(ex).show(childFragmentManager, ExerciceFragment.TAG)
        exID = _exID
        sessID = _sessID
    }

    override fun newSession(sessionID:Int) {
        sessID = sessionID
        EditSessionDialogFragment.editSessionInstance(Session(), ARG_SESSION_NEW)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }

    override fun editSession(sessionID:Int) {
        sessID = sessionID
        val session = MDatabase.getTodaySessions().get(sessID)
        EditSessionDialogFragment.editSessionInstance(session, ARG_SESSION_EDIT)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }
}