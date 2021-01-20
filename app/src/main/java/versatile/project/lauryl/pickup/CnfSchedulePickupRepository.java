package versatile.project.lauryl.pickup;

import android.text.TextUtils;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.orders.OrderRepository;
import versatile.project.lauryl.pickup.data.CnfPickupResponse;
import versatile.project.lauryl.pickup.data.DateTimeMap;

public class CnfSchedulePickupRepository extends OrderRepository {

    private SingleLiveEvent<Set<String>> cnfPickupResponseSingleLiveEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<String> cnfPickupErrorLiveEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isLastItemReachedEvent=new SingleLiveEvent<>();
    List<String> dateLists=new ArrayList<>();
    List<DateTimeMap> dateTimeMaps=new ArrayList<>();
    public SingleLiveEvent<Set<String>> getCnfPickupResponseSingleLiveEvent() {
        return cnfPickupResponseSingleLiveEvent;
    }

    public void getPickupDateTime(String accessToken, JsonObject jsonObject){
        getApiVersatileServices().getPickUpDateAndTime(accessToken,jsonObject).enqueue(new Callback<CnfPickupResponse>() {
            @Override
            public void onResponse(Call<CnfPickupResponse> call, Response<CnfPickupResponse> response) {
                if(response!=null && response.isSuccessful() && response.code()==200){
                    CnfPickupResponse cnfPickupResponse=response.body();
                    if(cnfPickupResponse.getData().getList().isEmpty()){
                        isLastItemReachedEvent.setValue(true);
                    }
                    for(CnfPickupResponse.DateTimeList dateTimeList:cnfPickupResponse.getData().getList()){
                        dateLists.add(dateTimeList.getPickUpDate());
                        DateTimeMap dateTimeMap=new DateTimeMap();
                        dateTimeMap.setDate(dateTimeList.getPickUpDate());
                        dateTimeMap.setTime(dateTimeList.getPickUpTime());
                        dateTimeMap.setNoOfSlots(dateTimeList.getNoOfSlots());
                        if(dateTimeList.getTimeSpan()!=null) {
                            dateTimeMap.setTimeSpan(Integer.parseInt(dateTimeList.getTimeSpan()));
                        }
                        dateTimeMaps.add(dateTimeMap);
                    }
                    Set<String> uniqueDates = new LinkedHashSet<>(dateLists);
                    cnfPickupResponseSingleLiveEvent.setValue(uniqueDates);
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

    public List<String> getTimesForDate(String date){
        List<String> timeList=new ArrayList<>();
        for(DateTimeMap dateTimeMap: dateTimeMaps){
            if(TextUtils.equals(dateTimeMap.getDate(),date)){
                timeList.add(dateTimeMap.getTime());
            }
        }
        return timeList;
    }
    public List<DateTimeMap> getTimesSlotsForDate(String date){
        List<DateTimeMap> timeList=new ArrayList<>();
        for(DateTimeMap dateTimeMap: dateTimeMaps){
            if(TextUtils.equals(dateTimeMap.getDate(),date)){
                timeList.add(dateTimeMap);
            }
        }
        return timeList;
    }

    public SingleLiveEvent<Boolean> getIsLastItemReachedEvent() {
        return isLastItemReachedEvent;
    }
}
