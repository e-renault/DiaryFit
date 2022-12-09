package ca.uqac.diaryfit.ui.adapters

import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.exercices.Exercice


class EditSessionCardViewAdapter(private val dataset:ArrayList<Exercice>,
                                 private val exerciceListener: ExerciceEditListener
) : RecyclerView.Adapter<EditSessionCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View,
                             val exerciceListener: ExerciceEditListener)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }
        val title_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_sub)

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
                            exerciceListener.deleteExerciceAction(bindingAdapterPosition)
                        }
                        ca.uqac.diaryfit.R.id.edit_exercice -> {
                            exerciceListener.editExerciceAction(bindingAdapterPosition)
                        }
                    }
                    return true
                }
            })
            dropDownMenu.show()
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
        fun editExerciceAction(exID: Int)
        fun deleteExerciceAction(exID: Int)
    }
}