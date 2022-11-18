package ca.uqac.diaryfit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment

class EditSessionCardViewAdapter(private val dataSet: List<Exercice>,
                                 val parentActivity: EditSessionDialogFragment
) : RecyclerView.Adapter<EditSessionCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_sub)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_exercice_editsession, parent, false)
        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, index: Int) {
        val ex: Exercice = dataSet[index]
        viewHolder.title_et.text = dataSet[index].getTitle()
        viewHolder.content_et.text =  dataSet[index].getDescription()
        viewHolder.view.setOnClickListener {
            parentActivity.editExercice(ex)
        }
    }

    override fun getItemCount() = dataSet.size
}