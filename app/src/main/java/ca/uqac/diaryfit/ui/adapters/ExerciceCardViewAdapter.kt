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

class ExerciceCardViewAdapter(val dataSet: ArrayList<Exercice>,
                              val sessionID: Int,
                              val exerciceListener: ExerciceEditListener
) : RecyclerView.Adapter<ExerciceCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View, val listener:ExerciceEditListener, val sessionID:Int):
        RecyclerView.ViewHolder(view), OnClickListener {
        init {
            view.setOnClickListener(this)
        }
        val title_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_sub)
        val done_cb: CheckBox = view.findViewById(R.id.cw_exercice_session_cb)
        val tool_bt: ImageButton = view.findViewById(R.id.cw_exercice_session_ib_tool)

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
        viewHolder.title_et.text = ex.getTitle()
        viewHolder.content_et.text = ex.getDescription()
        viewHolder.tool_bt.visibility =
            if (ex.hasTool() == true && !ex.isDone) View.VISIBLE else View.GONE
        viewHolder.done_cb.isChecked = ex.isDone == true
        viewHolder.done_cb.setOnClickListener {
            dataSet[exerciceID].isDone = viewHolder.done_cb.isChecked
            viewHolder.tool_bt.visibility =
                if (!dataSet[exerciceID].isDone) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount() = dataSet.size

    interface ExerciceEditListener {
        fun onClickOnCardview(exID: Int, sessID: Int)
    }
}