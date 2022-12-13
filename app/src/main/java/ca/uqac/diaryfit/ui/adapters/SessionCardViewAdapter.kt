package ca.uqac.diaryfit.ui.adapters

import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.Session


class SessionCardViewAdapter(private val dataset:ArrayList<Session>,
                             private val exerciceListener: SessionEditListener
) : RecyclerView.Adapter<SessionCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View,
                             val exerciceListener: SessionEditListener
    )
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }
        val title_et: TextView = view.findViewById(R.id.cw_session_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_session_tv_sub)

        override fun onClick(p0: View?) {
            val dropDownMenu =
                PopupMenu(view.context, view, Gravity.RIGHT)
            dropDownMenu.menuInflater.inflate(
                ca.uqac.diaryfit.R.menu.exercice_more_menu,
                dropDownMenu.menu
            )
            dropDownMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                    val pos: Int = bindingAdapterPosition
                    when (menuItem.itemId) {
                        ca.uqac.diaryfit.R.id.delete_exercice -> {
                            exerciceListener.deleteSessionAction(bindingAdapterPosition)
                        }
                        ca.uqac.diaryfit.R.id.edit_exercice -> {
                            exerciceListener.editSessionAction(bindingAdapterPosition)
                        }
                    }
                    return true
                }
            })
            dropDownMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_session_calendar, parent, false)
        return ExerciceViewHolder(view, exerciceListener)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, sessID: Int) {
        val sess: Session = dataset.get(sessID)
        viewHolder.title_et.text = sess.titleGet()
        viewHolder.content_et.text =  sess.descriptionGet()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface SessionEditListener {
        fun editSessionAction(sessID: Int)
        fun deleteSessionAction(sessID: Int)
    }
}