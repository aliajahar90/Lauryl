package versatile.project.lauryl.pickup.viewmodel;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.pickup.CnfSchedulePickupRepository;
import versatile.project.lauryl.pickup.data.CnfPickupResponse;

public class CnfSchedulePickupViewModel extends BaseViewModel {
    CnfSchedulePickupRepository cnfSchedulePickupRepository;
    public CnfSchedulePickupViewModel() {
        cnfSchedulePickupRepository=new CnfSchedulePickupRepository();
    }

    public void getPickUpDateTime(String accessToken, JsonObject jsonObject){
        cnfSchedulePickupRepository.getPickupDateTime(accessToken,jsonObject);
    }
    public LiveData<Map<String, List<String>>> observePickupDateTimeSuccessResponse(){
       return cnfSchedulePickupRepository.getCnfPickupResponseSingleLiveEvent();
    }
    public LiveData<String> observePickupDateTimeErrorResponse(){
       return cnfSchedulePickupRepository.getCnfPickupErrorLiveEvent();
    }
}
