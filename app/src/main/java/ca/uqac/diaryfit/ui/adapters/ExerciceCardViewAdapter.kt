package ca.uqac.diaryfit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.exercices.Exercice

class ExerciceCardViewAdapter(private val dataSet: ArrayList<Exercice>,
                              private val sessionID: Int,
                              private val exerciceListener: ExerciceMainListener
) : RecyclerView.Adapter<ExerciceCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(private val view: View, private val listener:ExerciceMainListener, private val sessionID:Int):
        RecyclerView.ViewHolder(view), OnClickListener {
        init {
            view.setOnClickListener(this)
        }
        val title_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_sub)
        val done_cb: CheckBox = view.findViewById(R.id.cw_exercice_session_cb)
        val tool_bt: ImageButton = view.findViewById(R.id.cw_exercice_session_ib_tool)

        fun tool_bt_click() {
            listener.onClickOnShortcut(bindingAdapterPosition, sessionID)
        }

        override fun onClick(p0: View?) {
            listener.onClickOnCardview(bindingAdapterPosition, sessionID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_exercice_session, parent, false)

        return ExerciceViewHolder(view ,exerciceListener, sessionID)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, exerciceID: Int) {
        val ex: Exercice = dataSet.get(exerciceID)
        viewHolder.title_et.text = ex.titleGet()
        viewHolder.content_et.text = ex.descriptionGet()
        viewHolder.tool_bt.visibility =
            if (ex.hasTool() && !ex.isDone) View.VISIBLE else View.GONE
        viewHolder.done_cb.isChecked = ex.isDone == true
        viewHolder.done_cb.setOnClickListener {
            dataSet[exerciceID].isDone = viewHolder.done_cb.isChecked
            viewHolder.tool_bt.visibility =
                if (!dataSet[exerciceID].isDone && ex.hasTool()) View.VISIBLE else View.GONE
        }
        viewHolder.tool_bt.setOnClickListener{
            viewHolder.tool_bt_click()
        }
    }

    override fun getItemCount() = dataSet.size

    interface ExerciceMainListener {
        fun onClickOnCardview(exID: Int, sessID: Int)
        fun onClickOnShortcut(exID: Int, sessID: Int)
    }
}