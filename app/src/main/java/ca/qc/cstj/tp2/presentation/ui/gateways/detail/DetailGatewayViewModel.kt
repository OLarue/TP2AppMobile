package ca.qc.cstj.tp2.presentation.ui.gateways.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailGatewayViewModel (private val href:String) : ViewModel(){



    init{
        viewModelScope.launch {

        }
    }

    //Pour le constructeur qui prend un parametre
    class Factory(private val href:String) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }
    }
}