package ca.uqac.diaryfit.ui.adapters

import android.view.*
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.datas.exercices.Exercice


class TodaySessionCardViewAdapter(val dataset:ArrayList<Session>,
                                  val exerciceListener: ExerciceCardViewAdapter.ExerciceEditListener,
                                  val sessionListener: SessionEditListener
) : RecyclerView.Adapter<TodaySessionCardViewAdapter.ExerciceViewHolder>(){

    private lateinit var exerciceAdapter: ExerciceCardViewAdapter

    class ExerciceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView? = view.findViewById(ca.uqac.diaryfit.R.id.cw_session_display_tv1)
        val done_cb: CheckBox? = view.findViewById(ca.uqac.diaryfit.R.id.cw_session_display_cb_main)
        val exercicelist_rv: RecyclerView? = view.findViewById(ca.uqac.diaryfit.R.id.cw_session_display_rv)
        var isCollapsed = false

        val plus_bt: ImageButton? = view.findViewById(ca.uqac.diaryfit.R.id.addnewelement_add_ib)
        val plus_info: TextView? = view.findViewById(ca.uqac.diaryfit.R.id.addnewelement_text_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val itemView: View

        if (viewType == ca.uqac.diaryfit.R.layout.cardview_session_display) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(ca.uqac.diaryfit.R.layout.cardview_session_display, parent, false)
        } else {
            itemView = LayoutInflater.from(parent.context)
                .inflate(ca.uqac.diaryfit.R.layout.cardview_add_new_element, parent, false)
        }

        return ExerciceViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, sessionID: Int) {
        if (sessionID == dataset.size) {
            viewHolder.plus_info?.text = "Add new Session"
            viewHolder.plus_bt?.setOnClickListener {
                sessionListener.newSession(sessionID)
            }
        } else {
            val session = dataset.get(sessionID)
            fun collaps() {
                viewHolder.isCollapsed = !viewHolder.isCollapsed
                val params = viewHolder.exercicelist_rv?.layoutParams
                params?.height =  if (!viewHolder.isCollapsed) ViewGroup.LayoutParams.WRAP_CONTENT else 0
                viewHolder.exercicelist_rv?.layoutParams = params
            }

            viewHolder.title_et?.text = session.getTitle()

            exerciceAdapter = ExerciceCardViewAdapter(session.getExerciceList2(), sessionID, exerciceListener)
            viewHolder.exercicelist_rv?.adapter = exerciceAdapter
            viewHolder.exercicelist_rv?.layoutManager = LinearLayoutManager(viewHolder.done_cb?.context)

            var status = true
            for (ex: Exercice in session.getExerciceList2()) {
                if (!ex.isDone) { status = false; break; }
            }
            viewHolder.done_cb?.isChecked =status
            viewHolder.done_cb?.setOnClickListener {
                for (ex: Exercice in session.getExerciceList2()) {
                    ex.isDone = viewHolder.done_cb.isChecked
                    viewHolder.exercicelist_rv?.adapter!!.notifyDataSetChanged()
                }
            }
            viewHolder.title_et?.setOnClickListener {
                val dropDownMenu =
                    PopupMenu(viewHolder.title_et.context, viewHolder.title_et, Gravity.RIGHT)
                dropDownMenu.menuInflater.inflate(
                    ca.uqac.diaryfit.R.menu.session_more_menu,
                    dropDownMenu.menu
                )
                dropDownMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                        val pos: Int = viewHolder.bindingAdapterPosition
                        when (menuItem.itemId) {
                            ca.uqac.diaryfit.R.id.collaps_session -> collaps()
                            ca.uqac.diaryfit.R.id.edit_session -> sessionListener.editSession(pos)
                            ca.uqac.diaryfit.R.id.new_session -> sessionListener.newSession(pos)
                        }
                        return true
                    }
                })
                dropDownMenu.show()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == dataset.size)
            return ca.uqac.diaryfit.R.layout.cardview_add_new_element
        else
            return ca.uqac.diaryfit.R.layout.cardview_session_display
    }

    override fun getItemCount() = dataset.size +1

    interface SessionEditListener {
        fun newSession(sessionID:Int)
        fun editSession(sessionID:Int)
    }

}