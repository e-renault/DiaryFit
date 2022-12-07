package ca.uqac.diaryfit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.exercices.Exercice

class EditSessionCardViewAdapter(val dataset:ArrayList<Exercice>,
                                 val exerciceListener: ExerciceEditListener
) : RecyclerView.Adapter<EditSessionCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View,
                             val exerciceListener: ExerciceEditListener)
        : RecyclerView.ViewHolder(view), OnClickListener, OnLongClickListener {
        init {
            view.setOnClickListener(this)
        }
        val title_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_sub)

        override fun onClick(p0: View?) {
            exerciceListener.onClickOnCardview(bindingAdapterPosition)
        }

        //TODO ne marche pas (Erwan)
        override fun onLongClick(p0: View?): Boolean {
            return exerciceListener.onLongPressCardview(bindingAdapterPosition, this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_exercice_editsession, parent, false)
        return ExerciceViewHolder(view, exerciceListener)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, exID: Int) {
        val ex: Exercice = dataset.get(exID)
        viewHolder.title_et.text = ex.titleGet()
        viewHolder.content_et.text =  ex.descriptionGet()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface ExerciceEditListener {
        fun onClickOnCardview(exID: Int)
        fun onLongPressCardview(exID: Int, viewHolder:ExerciceViewHolder): Boolean
    }
}