package versatile.project.lauryl.pickup;

import android.os.Build;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.model.TopServicesResponse;
import versatile.project.lauryl.pickup.data.CnfPickupResponse;

public class CnfSchedulePickupRepository extends LaurylRepository {

    private SingleLiveEvent<Map<String,List<String>>> cnfPickupResponseSingleLiveEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<String> cnfPickupErrorLiveEvent=new SingleLiveEvent<>();

    public SingleLiveEvent<Map<String, List<String>>> getCnfPickupResponseSingleLiveEvent() {
        return cnfPickupResponseSingleLiveEvent;
    }

    public void getPickupDateTime(String accessToken, JsonObject jsonObject){
        getApiVersatileServices().getPickUpDateAndTime(accessToken,jsonObject).enqueue(new Callback<CnfPickupResponse>() {
            @Override
            public void onResponse(Call<CnfPickupResponse> call, Response<CnfPickupResponse> response) {
                if(response!=null && response.isSuccessful() && response.code()==200){
                    CnfPickupResponse cnfPickupResponse=response.body();
                    Map<String, List<String>> stringListHashMap=new HashMap<>();
                    for(CnfPickupResponse.DateTimeList dateTimeList:cnfPickupResponse.getData().getList()){
                        List<String> times = null;
                       if(stringListHashMap.containsKey(dateTimeList.getPickUpDate())){
                           times.add(dateTimeList.getPickUpTime());
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                               stringListHashMap.replace(dateTimeList.getPickUpDate(),times);
                           }
                       }else {
                           times=new ArrayList<>();
                           times.add(dateTimeList.getPickUpTime());
                           stringListHashMap.put(dateTimeList.getPickUpDate(),times);
                       }
                    }
                    cnfPickupResponseSingleLiveEvent.setValue(stringListHashMap);
                }else {
                    cnfPickupErrorLiveEvent.setValue("Error fetching date and time");
                }
            }

            @Override
            public void onFailure(Call<CnfPickupResponse> call, Throwable t) {
                cnfPickupErrorLiveEvent.setValue(t.getMessage());
            }
        });
    }



    public SingleLiveEvent<String> getCnfPickupErrorLiveEvent() {
        return cnfPickupErrorLiveEvent;
    }
}
