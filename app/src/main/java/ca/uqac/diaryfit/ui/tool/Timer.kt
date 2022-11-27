package ca.uqac.diaryfit.ui.tool

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentTimerBinding

class Timer : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    var START_MILLI_SECONDS = 60000L

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var time_in_milli_seconds = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val view = binding.root

        val start_button = view.findViewById<Button>(R.id.bt_tool_start)
        val reset_button = view.findViewById<Button>(R.id.bt_tool_reset)

        val timer = view.findViewById<TextView>(R.id.tw_tool_timer)

        val time_edit_text = view.findViewById<EditText>(R.id.ed_tool_timer)

        val timer_pb = view.findViewById<ProgressBar>(R.id.progressBar)

        start_button.setOnClickListener {
            if (isRunning) {
                pauseTimer(start_button, reset_button)
            } else {
                val time  = time_edit_text.text.toString()
                time_in_milli_seconds = time.toLong() * 60000L / 60
                timer_pb.max = time_in_milli_seconds.toInt()
                startTimer(time_in_milli_seconds, reset_button, start_button, timer, timer_pb)
            }
        }

        reset_button.setOnClickListener {
            resetTimer(reset_button, timer, timer_pb)
        }

        return view
    }

    private fun pauseTimer(button: Button, reset: Button) {

        button.text = "Start"
        countdown_timer.cancel()
        isRunning = false
        reset.visibility = View.VISIBLE
    }

    private fun startTimer(
        time_in_seconds: Long,
        reset: Button,
        button: Button,
        timer: TextView,
        timer_pb: ProgressBar
    ) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                timer_pb.progress = 0
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                timer_pb.progress = time_in_milli_seconds.toInt()
                updateTextUI(timer)
            }
        }
        countdown_timer.start()

        isRunning = true
        button.text = "Pause"
        reset.visibility = View.INVISIBLE

    }

    private fun resetTimer(reset: Button, timer: TextView, timer_pb: ProgressBar) {
        time_in_milli_seconds = START_MILLI_SECONDS
        timer_pb.max = time_in_milli_seconds.toInt()
        updateTextUI(timer)
        reset.visibility = View.INVISIBLE
    }

    private fun updateTextUI(timer: TextView) {
        val minute = (time_in_milli_seconds / 1000) / 60
        val seconds = (time_in_milli_seconds / 1000) % 60

        timer.text = "$minute:$seconds"
    }
}