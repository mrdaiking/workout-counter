package demo.app.workoutcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import demo.app.workoutcounter.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var restProgress: Int = 0
    private var restTimer: CountDownTimer? = null // Variable for timer progress. As initial value the rest progress is set to 0. As we are about to start.
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration : Long = 30
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var binding : ActivityExerciseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()

        setupRestView()

    }

    // TODO (Step 3 - Setting up the Get Ready View with 10 seconds of timer.)
    /**
     * Function is used to get the timer for REST
     */
    private fun setupRestView() {
        /**
         * Here firstly we will check if the timer is running the and it is not null
         * then cancel the running timer and start the new one.
         * And set the progress to initial which is 0
         */
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        // This function is used to set the progress details
        setRestProgressBar()
    }

    // TODO (Step 2 - Setting up the 10 seconds timer for rest view and updating it continuously
    /**
     * Function is used to get the progress of timer using the progress
     */
    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 -restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
    }
    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "This is 30 seconds completed so now we will add all the exercises.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        super.onDestroy()
        binding = null
    }
}