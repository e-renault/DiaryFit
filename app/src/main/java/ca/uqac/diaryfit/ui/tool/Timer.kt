package ca.uqac.diaryfit.ui.tool

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.TimePickerFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ca.uqac.diaryfit.databinding.TabToolTimerBinding

private val ARG_TIME = "timer_time_argv"

class Timer : Fragment() {

    private var _binding: TabToolTimerBinding? = null
    private val binding get() = _binding!!

    private lateinit var countdown_timer: CountDownTimer
    private lateinit var time_edit_text: TextView
    private lateinit var timer: TextView

    private var isRunning: Boolean = false
    private var time_reset:MTime = MTime(0,1,0)
    private var time_in_milli_seconde:Long = time_reset.millisGet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_TIME, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_reset = time
            if (!isRunning) {
                time_in_milli_seconde = time_reset.millisGet()
            }
            updateTextUI(timer)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TabToolTimerBinding.inflate(inflater, container, false)
        val view = binding.root

        val start_button = view.findViewById<FloatingActionButton>(R.id.bt_tool_start)
        val reset_button = view.findViewById<FloatingActionButton>(R.id.bt_tool_reset)

        timer = view.findViewById<TextView>(R.id.tw_tool_timer)

        time_edit_text = view.findViewById<TextView>(R.id.ed_tool_timer)
        time_edit_text.setOnClickListener{
            TimePickerFragment.newInstance(time_reset, ARG_TIME)
                .show(childFragmentManager, ExerciceFragment.TAG)
        }

        val timer_pb = view.findViewById<ProgressBar>(R.id.progressBar)

        start_button.setOnClickListener {
            if (isRunning) {
                pauseTimer(start_button, reset_button)
            } else {
                startTimer(time_in_milli_seconde, reset_button, start_button, timer, timer_pb)
            }
        }

        reset_button.setOnClickListener {
            resetTimer(reset_button, timer, timer_pb)
        }

        updateTextUI(timer)
        return view
    }

    private fun pauseTimer(button: FloatingActionButton, reset: FloatingActionButton) {
        button.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        countdown_timer.cancel()
        isRunning = false
        reset.visibility = View.VISIBLE
    }

    private fun startTimer(
        time_in_seconds: Long,
        reset: FloatingActionButton,
        button: FloatingActionButton,
        timer: TextView,
        timer_pb: ProgressBar
    ) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                timer_pb.progress = 0
                updateTextUI(timer)
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconde = p0
                timer_pb.progress = time_in_milli_seconde.toInt()
                updateTextUI(timer)
            }
        }
        countdown_timer.start()

        isRunning = true
        button.setImageResource(ca.uqac.diaryfit.R.drawable.ic_baseline_pause_24)
        reset.visibility = View.INVISIBLE

    }

    private fun resetTimer(reset: FloatingActionButton, timer: TextView, timer_pb: ProgressBar) {
        time_in_milli_seconde = time_reset.millisGet()
        timer_pb.max = time_in_milli_seconde.toInt()
        updateTextUI(timer)
        reset.visibility = View.INVISIBLE
    }

    private fun updateTextUI(timer: TextView) {
        timer.text = MTime(time_in_milli_seconde).toString()
        time_edit_text.text = time_reset.toString()
    }
}