package versatile.project.lauryl.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import versatile.project.lauryl.data.source.LaurylRepository
import versatile.project.lauryl.model.city.CityModel

class MapLocationViewModel : ViewModel() {

    private var laurylRepository: LaurylRepository = LaurylRepository()

    private var citiesLiveData: LiveData<ArrayList<CityModel>>


    init {
        citiesLiveData = laurylRepository.citiesLiveData
    }

    fun getCitiesToObserve(): LiveData<ArrayList<CityModel>> {
        return citiesLiveData
    }

    fun getCities(access: String) {
        laurylRepository.getCities(access)
    }

}