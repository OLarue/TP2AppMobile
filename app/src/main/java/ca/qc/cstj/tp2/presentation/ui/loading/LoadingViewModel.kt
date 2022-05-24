package ca.qc.cstj.tp2.presentation.ui.loading

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoadingViewModel : ViewModel() {
    private val _loadingProgress = MutableLiveData<Int>()
    val loadingProgress: LiveData<Int> get() = _loadingProgress
    private val _isTimerDone = MutableLiveData<Boolean>()
    val isTimerDone: LiveData<Boolean> get() = _isTimerDone

    private val timer = object: CountDownTimer(10000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            incrementProgress()
        }

        override fun onFinish() {
            _isTimerDone.value = true
        }
    }

    init {
        _loadingProgress.value = 0
        timer.start()
    }

    fun incrementProgress() {
        _loadingProgress.value = _loadingProgress.value!!.plus(1)
    }


}