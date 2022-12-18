package ca.uqac.diaryfit.ui.tool


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.TabToolTabataBinding
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.datas.exercices.ExerciceTime
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment
import ca.uqac.diaryfit.ui.dialogs.TimePickerFragment
import ca.uqac.diaryfit.ui.tabs.ARG
import ca.uqac.diaryfit.ui.tabs.TABATA_SHORTCUT
import ca.uqac.diaryfit.ui.tabs.TIMER_SHORTCUT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


private val ARG_WORK = "tabata_work_argv"
private val ARG_REST = "tabata_rest_argv"
private val ARG_CYCLE = "tabata_cycle_argv"
private val ARG_PREP = "tabata_prepear_argv"
private val ARG_NB_EXO = "tabata_nb_exo_argv"

class Tabata : Fragment() {
    private var _binding: TabToolTabataBinding? = null
    private val binding get() = _binding!!

    private var time_rest:MTime = MTime(10,0,0)
    private var time_work:MTime = MTime(20,0,0)
    private var time_prep:MTime = MTime(30,0,0)
    private var nbcycle:Int = 2
    private var nbExo:Int = 2

    private var isRunning = false

    private lateinit var timer:CountDownTimer
    //UI
    private lateinit var cycle_bt: TextView
    private lateinit var restTime_bt: TextView
    private lateinit var workTime_bt: TextView
    private lateinit var preptime_bt: TextView
    private lateinit var NbExo_bt: TextView

    private var time:Long = 0
    private var i: Int = 0
    private var j: Int = 0

    private lateinit var text:TextView
    private lateinit var counter:TextView

    private lateinit var display: LinearLayout
    private lateinit var scrollView: ScrollView

    private lateinit var playButton: FloatingActionButton
    private lateinit var resetButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_WORK, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_work = time

            update()
        }

        childFragmentManager.setFragmentResultListener(ARG_REST, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_rest = time

            update()
        }
        childFragmentManager.setFragmentResultListener(ARG_PREP, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_prep = time

            update()
        }

        childFragmentManager.setFragmentResultListener(ARG_CYCLE, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")

            nbcycle = result
            update()
        }
        childFragmentManager.setFragmentResultListener(ARG_NB_EXO, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")

            nbExo = result
            update()
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(TABATA_SHORTCUT, this){ requestKey, bundle ->
            val ex: ExerciceTabata? = bundle.getParcelable(ARG)
            if (ex != null) {
                time_rest = ex.rest
                time_work = ex.effortTime
                nbcycle = ex.nbCycle
                nbExo = ex.otherExerciceList.size
                val time_set = (time_prep.millisGet()+nbExo*(time_work.millisGet()+time_rest.millisGet()))
                time = time_set * nbcycle

                cycle_bt.text = nbcycle.toString()
                NbExo_bt.text = nbExo.toString()
                restTime_bt.text = time_rest.toString()
                workTime_bt.text = time_work.toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabToolTabataBinding.inflate(inflater, container, false)
        val view = binding.root

        cycle_bt = view.findViewById(R.id.tw_tabata_bt_cycle)
        cycle_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 50, nbcycle, ARG_CYCLE).show(childFragmentManager, ExerciceFragment.TAG)
        }

        restTime_bt = view.findViewById(R.id.tw_tabata_bt_rest)
        restTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(time_rest, ARG_REST).show(childFragmentManager, ExerciceFragment.TAG)
        }

        workTime_bt = view.findViewById(R.id.tw_tabata_bt_work)
        workTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(time_work, ARG_WORK).show(childFragmentManager, ExerciceFragment.TAG)
        }

        preptime_bt = view.findViewById(R.id.tw_tabata_bt_prepare)
        preptime_bt.setOnClickListener {
            TimePickerFragment.newInstance(time_prep, ARG_PREP).show(childFragmentManager, ExerciceFragment.TAG)
        }

        NbExo_bt = view.findViewById(R.id.tw_tabata_bt_nbEx)
        NbExo_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 50, nbExo, ARG_NB_EXO).show(childFragmentManager, ExerciceFragment.TAG)
        }


        display = view.findViewById(R.id.display)
        counter = view.findViewById(R.id.tw_tabata_counter)
        text = view.findViewById(R.id.tw_tabata_text)

        scrollView = view.findViewById(R.id.scrollView2)

        playButton = view.findViewById(R.id.tw_tabata_bt_play)
        val time_set = (time_prep.millisGet()+nbExo*(time_work.millisGet()+time_rest.millisGet()))
        time = time_set * nbcycle
        playButton.setOnClickListener {
            if(!isRunning){
                play(time)
            }
            else
                pause()
        }

        resetButton = view.findViewById(R.id.tw_tabata_bt_reset)
        resetButton.setOnClickListener {
            reset()
        }

        update()
        return view
    }

    private fun pause() {
        isRunning = false
        timer.cancel()
        playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }

    private fun reset() {
        display.visibility = View.GONE
        scrollView.visibility = View.VISIBLE
        playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        i = nbcycle
        j = nbExo
        val time_set = (time_prep.millisGet()+nbExo*(time_work.millisGet()+time_rest.millisGet()))
        time = time_set * nbcycle
    }

    @SuppressLint("SetTextI18n")
    private fun play(_time: Long) {
        isRunning = true
        scrollView.visibility = View.GONE
        display.visibility = View.VISIBLE
        playButton.setImageResource(ca.uqac.diaryfit.R.drawable.ic_baseline_pause_24)

        val time_set = (time_prep.millisGet()+nbExo*(time_work.millisGet()+time_rest.millisGet()))
        val time_set_exo = (time_work.millisGet() + time_rest.millisGet())
        timer = object : CountDownTimer(_time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished
                if (millisUntilFinished > time_set*(i) - time_prep.millisGet()) {
                    j = nbExo
                    text.text = "Be ready !"
                    counter.text =
                        MTime(millisUntilFinished - (time_set*(i) - time_prep.millisGet())).toString()
                } else if (millisUntilFinished >  time_set*(i-1) + time_set_exo*(j-1) + time_rest.millisGet()) {
                    text.text = "Work !"
                    counter.text = MTime(millisUntilFinished - (time_set*(i-1) + time_set_exo*(j-1) + time_rest.millisGet())).toString()
                } else if (millisUntilFinished > time_set * (i-1) + time_set_exo * (j-1)) {
                    if(i != 1 || j != 1){
                        text.text = "Rest"
                        counter.text = MTime(millisUntilFinished - (time_set * (i-1) + time_set_exo * (j-1))).toString()
                    }
                    else{
                        text.text = "Finish"
                        counter.text = ""
                    }
                }
                if(millisUntilFinished <= time_set*(i-1) + time_set_exo*(j-1)) {
                    j--
                }
                if(millisUntilFinished <= time_set*(i-1)){
                    i--
                }
            }
            override fun onFinish() {
                display.visibility = View.GONE
                scrollView.visibility = View.VISIBLE
                playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }.start()
    }

    private fun update() {
        cycle_bt.text = nbcycle.toString()
        restTime_bt.text = time_rest.toString()
        workTime_bt.text = time_work.toString()
        preptime_bt.text = time_prep.toString()
        NbExo_bt.text = nbExo.toString()
        val time_set = (time_prep.millisGet()+nbExo*(time_work.millisGet()+time_rest.millisGet()))
        time = time_set *nbcycle
        i = nbcycle
        j = nbExo
    }
}