package ca.uqac.diaryfit.ui.rvAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.Exercice

class TodaySessionCardViewAdapter(private val dataSet: List<Exercice>) : RecyclerView.Adapter<TodaySessionCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(R.id.cw_exercice_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_tv_sub)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_edit_session, parent, false)
        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, index: Int) {
        viewHolder.title_et.text = dataSet[index].getTitle()
        viewHolder.content_et.text =  dataSet[index].getDescription()
    }

    override fun getItemCount() = dataSet.size
}