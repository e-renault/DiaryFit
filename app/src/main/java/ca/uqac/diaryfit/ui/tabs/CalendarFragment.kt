package ca.uqac.diaryfit.ui.tabs

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.datas.MDate
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.ui.adapters.SessionCardViewAdapter
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_DIALOG_RET
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_EDIT
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_NEW
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidListener
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(),
    SessionCardViewAdapter.SessionEditListener {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var add_btn : FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciceAdapter: SessionCardViewAdapter
    private lateinit var calendrier:CaldroidFragment

    private var selectedSessionList:ArrayList<Session> = ArrayList<Session>()
    private var backgroundForDateMap: HashMap<Date, Drawable> = HashMap()
    private var selectedDate:MDate = MDate.getTodayDate()
    private var selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedYear = Calendar.getInstance().get(Calendar.YEAR)
    private var sessID:Int = -1

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        calendrier.saveStatesToKey(outState, "CALDROID_SAVED_STATE")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_SESSION_DIALOG_RET, this) {
                requestKey, bundle ->
            val new = bundle.getParcelable<Session>(ARG_SESSION_NEW)
            val edit = bundle.getParcelable<Session>(ARG_SESSION_EDIT)

            if (new != null) {
                selectedSessionList.add(new)
                UserDB.addSession(MainActivity.profil, selectedDate.toString(), new)
                updateData()
            }

            if (edit != null) {
                selectedSessionList[sessID] = edit
                UserDB.setSession(MainActivity.profil, selectedDate.toString(), selectedSessionList)
                updateData()
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

        recyclerView = root.findViewById(ca.uqac.diaryfit.R.id.frgcalendar_rv) as RecyclerView
        selectedSessionList.clear()
        selectedSessionList.addAll(UserDB.getSession(MainActivity.profil, selectedDate.toString()) as ArrayList<Session>)
        exerciceAdapter = SessionCardViewAdapter(selectedSessionList, this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        add_btn = root.findViewById(ca.uqac.diaryfit.R.id.frgcalendar_fab)
        add_btn.setOnClickListener { newSession() }


        calendrier = CaldroidFragment()

        if (savedInstanceState != null) {
            calendrier.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE")
        } else {
            val cal: Calendar = Calendar.getInstance()
            val args = Bundle()
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1)
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR))
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true)
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true)
            calendrier.arguments = args

        }
        requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.container_caldroid , calendrier ).commit()

        val listener: CaldroidListener = object : CaldroidListener() {
            override fun onSelectDate(date: Date?, view: View?) {
                if (date != null) {
                    selectNewDate(MDate(date))
                }
            }
            override fun onChangeMonth(month: Int, year: Int) {
                selectedMonth = month
                selectedYear = year
                updateData()
            }
            override fun onLongClickDate(date: Date?, view: View?) { }
            override fun onCaldroidViewCreated() {
                updateData()
            }
        }

        calendrier.caldroidListener = listener
        selectNewDate(selectedDate)

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
        UserDB.setSession(MainActivity.profil, selectedDate.toString(), selectedSessionList)
        updateData()
    }

    private fun updateData() {

        val month: ArrayList<Session> = ArrayList()

        for (day_incr in 1..31) {
            month.addAll(
                UserDB.getSession(
                    MainActivity.profil,
                    "%04d-%02d-%02d".format(selectedYear, selectedMonth, day_incr)
                ) as ArrayList<Session>
            )
        }

        for (session in month) {
            val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(session.timeDate) as Date
            if (!backgroundForDateMap.containsKey(date)) {
                ContextCompat.getDrawable(recyclerView.context, R.drawable.custom_cell_caldroid)
                    ?.let { backgroundForDateMap.put(date, it) }
            }
        }

        selectNewDate(selectedDate)
    }

    fun selectNewDate(newDate:MDate) {
        val oldDate = selectedDate
        selectedDate = newDate

        selectedSessionList.clear()
        selectedSessionList.addAll(UserDB.getSession(MainActivity.profil, selectedDate.toString()) as ArrayList<Session>)
        recyclerView.adapter?.notifyDataSetChanged()

        //TODO optimize (Erwan)
        val copy = HashMap(backgroundForDateMap)
        copy.put(selectedDate.getDate(), ContextCompat.getDrawable(recyclerView.context, R.drawable.custom_cell_caldroid_focus))
        calendrier.setBackgroundDrawableForDates(copy)
        calendrier.refreshView()
    }
}