package ca.uqac.diaryfit.ui.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_EDIT
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_NEW
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
import ca.uqac.diaryfit.ui.tabs.MainFragment

class TodaySessionCardViewAdapter(val dataset:ArrayList<Session>,
                                  val exerciceListener: ExerciceCardViewAdapter.ExerciceEditListener,
                                  val sessionListener: SessionEditListener
) : RecyclerView.Adapter<TodaySessionCardViewAdapter.ExerciceViewHolder>(){

    private lateinit var exerciceAdapter: ExerciceCardViewAdapter

    class ExerciceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(ca.uqac.diaryfit.R.id.cw_session_display_tv1)
        val done_cb: CheckBox = view.findViewById(ca.uqac.diaryfit.R.id.cw_session_display_cb_main)
        val exercicelist_rv: RecyclerView = view.findViewById(ca.uqac.diaryfit.R.id.cw_session_display_rv)
        var isCollapsed = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(ca.uqac.diaryfit.R.layout.cardview_session_display, parent, false)
        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, sessionID: Int) {
        val session = dataset.get(sessionID)
        fun collaps() {
            viewHolder.isCollapsed = !viewHolder.isCollapsed
            val params = viewHolder.exercicelist_rv.layoutParams
            params.height =  if (!viewHolder.isCollapsed) ViewGroup.LayoutParams.WRAP_CONTENT else 0
            viewHolder.exercicelist_rv.layoutParams = params
        }

        viewHolder.title_et.text = session.getTitle()

        exerciceAdapter = ExerciceCardViewAdapter(session.getExerciceList(), sessionID, exerciceListener)
        viewHolder.exercicelist_rv.adapter = exerciceAdapter
        viewHolder.exercicelist_rv.layoutManager = LinearLayoutManager(viewHolder.done_cb.context)

        var status = true
        for (ex: Exercice in session.getExerciceList()) {
            if (!ex.isDone) { status = false; break; }
        }
        viewHolder.done_cb.isChecked =status
            viewHolder.done_cb.setOnClickListener {
            for (ex: Exercice in session.getExerciceList()) {
                ex.isDone = viewHolder.done_cb.isChecked
                viewHolder.exercicelist_rv.adapter!!.notifyDataSetChanged()
            }
        }
        viewHolder.title_et.setOnClickListener {
            val dropDownMenu = PopupMenu(viewHolder.title_et.context, viewHolder.title_et, Gravity.RIGHT)
            dropDownMenu.menuInflater.inflate(R.menu.session_more_menu, dropDownMenu.menu)
            dropDownMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                    val pos:Int = viewHolder.bindingAdapterPosition
                    when(menuItem.itemId) {
                        R.id.collaps_session -> collaps()
                        R.id.edit_session ->  sessionListener.editSession(pos)
                        R.id.new_session -> sessionListener.newSession(pos)
                    }
                    return true
                }
            })
            dropDownMenu.show()
        }
    }

    override fun getItemCount() = dataset.size

    interface SessionEditListener {
        fun newSession(sessionID:Int)
        fun editSession(sessionID:Int)
    }

}