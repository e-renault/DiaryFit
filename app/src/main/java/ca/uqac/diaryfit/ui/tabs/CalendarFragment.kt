package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.User
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.datas.MDate
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.ui.adapters.ExerciceCardViewAdapter
import ca.uqac.diaryfit.ui.adapters.SessionCardViewAdapter
import ca.uqac.diaryfit.ui.adapters.TodaySessionCardViewAdapter
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_DIALOG_RET
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_EDIT
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_NEW
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarFragment : Fragment(),
    SessionCardViewAdapter.SessionEditListener {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendar : CalendarView
    private lateinit var add_btn : FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciceAdapter: SessionCardViewAdapter
    private var selectedSessionList:ArrayList<Session> = ArrayList()

    private var selectedDate:MDate = MDate.getTodayDate()
    private var sessID:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_SESSION_DIALOG_RET, this) {
                requestKey, bundle ->
            val new = bundle.getParcelable<Session>(ARG_SESSION_NEW)
            val edit = bundle.getParcelable<Session>(ARG_SESSION_EDIT)

            if (new != null) {
                selectedSessionList.add(new)
                recyclerView.adapter?.notifyDataSetChanged()
                UserDB.addSession(MainActivity.profil, selectedDate.toString(), new)
                //updateDB()
            }

            if (edit != null) {
                selectedSessionList.set(sessID, edit)
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
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.frgcalendar_rv) as RecyclerView
        selectedSessionList.clear()
        selectedSessionList.addAll(UserDB.getSession(MainActivity.profil, selectedDate.toString()) as ArrayList<Session>)
        exerciceAdapter = SessionCardViewAdapter(selectedSessionList, this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        calendar = root.findViewById(R.id.vw_calendar)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = MDate(year, month, dayOfMonth)
            selectedSessionList.clear()
            selectedSessionList.addAll(UserDB.getSession(MainActivity.profil, selectedDate.toString()) as ArrayList<Session>)
            println(selectedSessionList)
            recyclerView.adapter?.notifyDataSetChanged()
        }


        add_btn = root.findViewById(R.id.frgcalendar_fab)
        add_btn.setOnClickListener {
            newSession()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun newSession() {
        val session:Session = Session()
        session.timeDate = selectedDate.toString()
        EditSessionDialogFragment.editSessionInstance(session, ARG_SESSION_NEW)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }

    override fun editSessionAction(_sessID: Int) {
        sessID = _sessID
        val session = selectedSessionList.get(_sessID)
        EditSessionDialogFragment.editSessionInstance(session, ARG_SESSION_EDIT)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }

    override fun deleteSessionAction(sessID: Int) {
        selectedSessionList.remove(selectedSessionList.get(sessID))
        recyclerView.adapter?.notifyDataSetChanged()
        updateDB()
    }

    private fun updateDB() {
        UserDB.setSession(MainActivity.profil, selectedDate.toString(), selectedSessionList)
    }
}