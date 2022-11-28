package ca.uqac.diaryfit.ui.tool

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import ca.uqac.diaryfit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ca.uqac.diaryfit.databinding.TabToolChronoBinding

class Chrono : Fragment() {

    private var _binding: TabToolChronoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TabToolChronoBinding.inflate(inflater, container, false)
        val view = binding.root

        val chrono = view.findViewById<Chronometer>(R.id.fg_tools_chrono)

        var isWorking = false
        var pauseOffset = 0

        val start_btn = view.findViewById<FloatingActionButton>(R.id.fg_tools_btn_start)
        val reset_btn = view.findViewById<FloatingActionButton>(R.id.fg_tools_btn_reset)

        start_btn?.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                if (!isWorking) {
                    chrono.base = SystemClock.elapsedRealtime() - pauseOffset
                    chrono.start()
                    isWorking = true
                } else {
                    chrono.stop()
                    pauseOffset = (SystemClock.elapsedRealtime() - chrono.base).toInt()
                    isWorking = false
                }

                start_btn.setImageResource(if (isWorking) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24)

                /**Toast.makeText(requireActivity(), getString(
                    if (isWorking)
                        R.string.working
                    else
                        R.string.stopped
                ), Toast.LENGTH_SHORT).show()**/
            }
        })


        reset_btn?.setOnClickListener(object : View.OnClickListener{

            override fun onClick(p0: View?) {
                pauseOffset = 0
                chrono.base = SystemClock.elapsedRealtime()
                chrono.stop()
                isWorking = false

                start_btn.setImageResource(if (isWorking) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24)

                Toast.makeText(requireActivity(),"Time reset", Toast.LENGTH_SHORT).show()
            }
        })
        
        return view
    }
}