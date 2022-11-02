package ca.uqac.diaryfit.ui.rvAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.Exercice
import ca.uqac.diaryfit.ui.tabs.MainFragment

class ExerciceCardViewAdapter(private val dataSet: List<Exercice>,
                              val parentActivity: MainFragment
) : RecyclerView.Adapter<ExerciceCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(R.id.cw_exercice_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_tv_sub)
        val done_cb: CheckBox = view.findViewById(R.id.cw_sessex_cb)
        val tool_bt: ImageButton = view.findViewById(R.id.cw_sessex_ib_tool)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_session, parent, false)

        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, index: Int) {
        val ex:Exercice = dataSet[index]
        viewHolder.title_et.text = dataSet[index].getTitle()
        viewHolder.content_et.text =  dataSet[index].getDescription()
        viewHolder.tool_bt.visibility = if (dataSet[index].getTool()) View.VISIBLE else View.GONE
        viewHolder.done_cb.isChecked = dataSet[index].isDone
        viewHolder.done_cb.setOnClickListener {
            dataSet[index].isDone = viewHolder.done_cb.isChecked
        }
        viewHolder.view.setOnClickListener {
            parentActivity.editExercice(ex)
        }
    }

    override fun getItemCount() = dataSet.size
}