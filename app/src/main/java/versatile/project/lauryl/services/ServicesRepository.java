package versatile.project.lauryl.services;

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
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.model.TopServicesDataItem;
import versatile.project.lauryl.model.TopServicesResponse;
import versatile.project.lauryl.utils.AllConstants;

public class ServicesRepository extends LaurylRepository {

  private SingleLiveEvent<List<ServiceModel>> listServiceModelSingleLiveEvent=new SingleLiveEvent<>();
  private SingleLiveEvent<String> stringErrorServiceLoadSingleLiveEvent=new SingleLiveEvent<>();
    public void getAllServices(String accessToken, JsonObject jsonObject) {

        getApiVersatileServices().getTopServices(accessToken, jsonObject).enqueue(new Callback<TopServicesResponse>() {
            @Override
            public void onResponse(Call<TopServicesResponse> call, Response<TopServicesResponse> response) {
                if(response!=null &&  response.isSuccessful() && response.body()!=null && response.body().getData()!=null){
                    List<ServiceModel> serviceModels=new ArrayList<>();
                    List<String> serviceTypeList=new ArrayList<>();
                    List<TopServicesDataItem> topServicesDataItemList=new ArrayList<>();
                    topServicesDataItemList.addAll(response.body().getData().getList());
                    for(TopServicesDataItem topServicesDataItem: response.body().getData().getList()){
                        serviceTypeList.add(topServicesDataItem.getServiceType());
                    }
                    Set<String> uniqueServiceTypes = new LinkedHashSet<>(serviceTypeList);
                   for(String type:uniqueServiceTypes){
                       ServiceModel serviceModel=new ServiceModel();
                       serviceModel.setServiceType(true);
                       serviceModel.setServiceTypeName(type);
                       serviceModels.add(serviceModel);
                       for(TopServicesDataItem topServicesDataItem:getServicesForThisType(type,topServicesDataItemList)){
                           ServiceModel childServiceModel=new ServiceModel();
                           childServiceModel.setServiceType(false);
                           childServiceModel.setTopServicesDataItem(topServicesDataItem);
                           serviceModels.add(childServiceModel);
                       }
                   }
                   listServiceModelSingleLiveEvent.postValue(serviceModels);
                }
                else {
                    stringErrorServiceLoadSingleLiveEvent.postValue(AllConstants.Services.Errors.ERROR_LIST_EMPTY);
                }
            }

            @Override
            public void onFailure(Call<TopServicesResponse> call, Throwable t) {
                stringErrorServiceLoadSingleLiveEvent.postValue(AllConstants.Services.Errors.ERROR_API_FAILED);
            }
        });

    }

    public List<TopServicesDataItem> getServicesForThisType(String type,List<TopServicesDataItem> topServicesDataItems){
        List<TopServicesDataItem> timeList=new ArrayList<>();
        for(TopServicesDataItem dataItem: topServicesDataItems){
            if(TextUtils.equals(dataItem.getServiceType(),type)){
                timeList.add(dataItem);
            }
        }
        return timeList;
    }

    public SingleLiveEvent<List<ServiceModel>> getListServiceModelSingleLiveEvent() {
        return listServiceModelSingleLiveEvent;
    }

    public SingleLiveEvent<String> getStringErrorServiceLoadSingleLiveEvent() {
        return stringErrorServiceLoadSingleLiveEvent;
    }
}
