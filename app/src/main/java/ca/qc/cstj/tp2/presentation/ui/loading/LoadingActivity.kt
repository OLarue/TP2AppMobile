package ca.qc.cstj.tp2.presentation.ui.loading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import ca.qc.cstj.tp2.MainActivity
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.databinding.ActivityLoadingBinding

class LoadingActivity: AppCompatActivity() {

    private val binding by viewBinding<ActivityLoadingBinding>()
    private val viewModel by viewModels<LoadingViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        viewModel.loadingProgress.observe(this) {
            binding.txvLoading.text = getString(R.string.LoadingText, it, Constants.LOADING_MAX)
            binding.pgbLoading.setProgress(it, true)
        }

        viewModel.isTimerDone.observe(this) {
            if(it)
                startActivity(MainActivity.newIntent(this))
        }
    }
}





