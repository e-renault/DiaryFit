package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.databinding.FragmentMainBinding
import ca.uqac.diaryfit.datas.MDate
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.adapters.ExerciceCardViewAdapter
import ca.uqac.diaryfit.ui.adapters.TodaySessionCardViewAdapter
import ca.uqac.diaryfit.ui.dialogs.*
import kotlin.collections.ArrayList

class MainFragment : Fragment(),
    ExerciceCardViewAdapter.ExerciceMainListener,
    TodaySessionCardViewAdapter.SessionEditListener{
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //UI
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciceAdapter: TodaySessionCardViewAdapter
    private var TodaySession:ArrayList<Session> = ArrayList()

    private var exID:Int = -1
    private var sessID:Int = -1

    private var today:String = MDate.getTodayDate().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener("ExerciceDialogReturn", this) {
                requestKey, bundle ->
            val result = bundle.getParcelable<Exercice>(getString(R.string.Exercice))

            if (result != null) {
                TodaySession.get(sessID).exSet(exID, result)
                recyclerView.adapter?.notifyDataSetChanged()
                updateDB()
            }
        }

        childFragmentManager.setFragmentResultListener(ARG_SESSION_DIALOG_RET, this) {
                requestKey, bundle ->
            val edit = bundle.getParcelable<Session>(ARG_SESSION_EDIT)
            val new = bundle.getParcelable<Session>(ARG_SESSION_NEW)

            if (edit != null) {
                TodaySession.set(sessID, edit)
                recyclerView.adapter?.notifyDataSetChanged()
                updateDB()
            }

            if (new != null) {
                TodaySession.add(new)
                recyclerView.adapter?.notifyDataSetChanged()
                updateDB()
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

        today = MDate.getTodayDate().toString()
        TodaySession = UserDB.getSession(MainActivity.profil, today) as ArrayList<Session>

        recyclerView = root.findViewById(R.id.frgmain_rv) as RecyclerView
        exerciceAdapter = TodaySessionCardViewAdapter(TodaySession, this, this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickOnCardview(_exID: Int, _sessID: Int) {
        val ex: Exercice = TodaySession.get(_sessID).exGet(_exID)
        ExerciceFragment.editExercice(ex).show(childFragmentManager, ExerciceFragment.TAG)
        exID = _exID
        sessID = _sessID
    }

    override fun onClickOnShortcut(_exID: Int, _sessID: Int) {
        //TODO generate link
        Toast.makeText(context, "Shortcut clicked (${_exID}, ${_sessID})", Toast.LENGTH_SHORT).show()
    }

    override fun newSession(sessionID:Int) {
        sessID = sessionID
        val session:Session = Session()
        session.timeDate = MDate.getTodayDate().toString()
        EditSessionDialogFragment.editSessionInstance(session, ARG_SESSION_NEW)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }

    override fun editSession(sessionID:Int) {
        sessID = sessionID
        val session = TodaySession.get(sessID)
        EditSessionDialogFragment.editSessionInstance(session, ARG_SESSION_EDIT)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }

    override fun deleteSession(sessionID: Int) {
        TodaySession.remove(TodaySession.get(sessionID))
        recyclerView.adapter?.notifyDataSetChanged()
        updateDB()
    }

    private fun updateDB() {
        UserDB.setSession(MainActivity.profil, today, TodaySession)
    }
}