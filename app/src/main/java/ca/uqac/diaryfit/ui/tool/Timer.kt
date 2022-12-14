package ca.uqac.diaryfit.ui.tool

import android.media.MediaPlayer
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
import com.google.android.material.progressindicator.CircularProgressIndicator

private val ARG_TIME = "timer_time_argv"

class Timer : Fragment() {

    private var _binding: TabToolTimerBinding? = null
    private val binding get() = _binding!!
    private var alarm: MediaPlayer? = null
    private lateinit var countdown_timer: CountDownTimer
    private lateinit var time_edit_text: TextView
    private lateinit var timer: TextView
    private lateinit var timer_pb : CircularProgressIndicator
    private lateinit var reset_button : FloatingActionButton

    private var isRunning: Boolean = false
    private var time_reset:MTime = MTime(0,1,0)
    private var time_in_milli_seconde:Long = time_reset.millisGet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarm = MediaPlayer.create(context, R.raw.alarm)
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
        reset_button = view.findViewById(R.id.bt_tool_reset)

        timer = view.findViewById<TextView>(R.id.tw_tool_timer)

        time_edit_text = view.findViewById<TextView>(R.id.ed_tool_timer)
        time_edit_text.setOnClickListener{
            if(!isRunning)
                TimePickerFragment.newInstance(time_reset, ARG_TIME)
                    .show(childFragmentManager, ExerciceFragment.TAG)
        }

        timer_pb = view.findViewById(R.id.progressBar)

        start_button.setOnClickListener {
            if (isRunning) {
                pauseTimer(start_button, reset_button)
            } else {
                startTimer(time_in_milli_seconde, reset_button, start_button, timer)
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
        alarm?.pause()
    }

    private fun startTimer(
        time_in_seconds: Long,
        reset: FloatingActionButton,
        button: FloatingActionButton,
        timer: TextView
    ) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                timer_pb.progress = 0
                timer.text = MTime(0).toString()
                alarm?.isLooping = true
                alarm?.start()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconde = p0
                println(time_in_milli_seconde)
                timer_pb.progress = (time_in_milli_seconde.toDouble()/time_in_seconds.toDouble()*100).toInt()
                updateTextUI(timer)
            }
        }
        countdown_timer.start()

        isRunning = true
        button.setImageResource(ca.uqac.diaryfit.R.drawable.ic_baseline_pause_24)
        reset.visibility = View.GONE

    }

    private fun resetTimer(reset: FloatingActionButton, timer: TextView, timer_pb: ProgressBar) {
        time_in_milli_seconde = time_reset.millisGet()
        updateTextUI(timer)
        reset.visibility = View.GONE
        alarm?.pause()
    }

    private fun updateTextUI(timer: TextView) {
        timer.text = MTime(time_in_milli_seconde).toString()
        time_edit_text.text = time_reset.toString()
    }
}