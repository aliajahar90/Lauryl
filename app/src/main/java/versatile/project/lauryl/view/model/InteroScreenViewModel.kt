package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InteroScreenViewModel : ViewModel() {

    var introCounter: Int = 1
    var introCounterMutableLiveDta: MutableLiveData<Int> = MutableLiveData()

    fun getInitialCounter(): LiveData<Int> {

        introCounterMutableLiveDta.value = introCounter
        return introCounterMutableLiveDta

    }

    fun incrementCounter(): LiveData<Int> {

        introCounter += 1
        introCounterMutableLiveDta.value = introCounter
        return introCounterMutableLiveDta

    }

}