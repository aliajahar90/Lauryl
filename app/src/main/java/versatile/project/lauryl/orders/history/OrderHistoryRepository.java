package versatile.project.lauryl.orders.history;

import android.text.TextUtils;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.R;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.orders.history.model.OrderItemsResponse;
import versatile.project.lauryl.orders.history.model.ServiceItemType;
import versatile.project.lauryl.orders.history.model.ServiceType;
import versatile.project.lauryl.profile.ProfileRepository;
import versatile.project.lauryl.utils.AllConstants;

public class OrderHistoryRepository  extends LaurylRepository {
private SingleLiveEvent<List<ServiceType>> listServiceSkuTypeSingleLiveEvent= new SingleLiveEvent<>();
private SingleLiveEvent<List<OrderItemsResponse.ServiceItem>> listRawListSingleLiveEvent= new SingleLiveEvent<>();
private SingleLiveEvent<String> stringErrorSingleLiveEvent=new SingleLiveEvent<>();
    Map<String,ServiceItemType> stringServiceItemTypeMap;;
    void getOrderItems(String accessToken, JsonObject orderHistoryRequest){
        getApiVersatileServices().getOrderItems(accessToken,orderHistoryRequest).enqueue(new Callback<OrderItemsResponse>() {
            @Override
            public void onResponse(Call<OrderItemsResponse> call, Response<OrderItemsResponse> response) {
                if(response!=null && response.isSuccessful() && response.code()==200){
                    if(response.body()!=null && response.body().getData()!=null && !(response.body().getData().getList().isEmpty())){
                        stringServiceItemTypeMap=new HashMap<>();
                        List<String> serviceItems=new ArrayList<>();
                        Map<String, String> nameMap=new HashMap<>();
                        Map<String, List<ServiceItemType>> stringListHashMap=new HashMap<>();
                        List<ServiceType> serviceTypeList=new ArrayList<>();
                        for(OrderItemsResponse.ServiceItem  serviceItem:response.body().getData().getList()){
                            serviceItems.add(serviceItem.getSku());
                            nameMap.put(serviceItem.getSku(),serviceItem.getProductName());
                            ServiceItemType serviceItemType=new ServiceItemType(serviceItem.getScannedItemType(),serviceItem.getQtyPurchased(),serviceItem.getProductPrice());
                            insertInServiceItemMap(stringListHashMap,serviceItem.getSku(),serviceItemType);
                        }
                        Set<String> uniqueServices =new LinkedHashSet<>(serviceItems);
                                for(String service:uniqueServices){
                                    ServiceType serviceType=new ServiceType(nameMap.get(service),stringListHashMap.get(service));
                                    serviceTypeList.add(serviceType);
                                }
                        listServiceSkuTypeSingleLiveEvent.setValue(serviceTypeList);
                         listRawListSingleLiveEvent.setValue(response.body().getData().getList());
                    }else {
                        stringErrorSingleLiveEvent.setValue(AllConstants.Orders.ORDER_ITEM_ERROR_EMPTY);
                    }
                }else {
                    stringErrorSingleLiveEvent.setValue(AllConstants.Orders.ORDER_ITEM_ERROR_API);
                }
            }

            @Override
            public void onFailure(Call<OrderItemsResponse> call, Throwable t) {
                stringErrorSingleLiveEvent.setValue(t.getMessage());
            }
        });
    }

    public SingleLiveEvent<List<ServiceType>> getListServiceSkuTypeSingleLiveEvent() {
        return listServiceSkuTypeSingleLiveEvent;
    }

    public SingleLiveEvent<List<OrderItemsResponse.ServiceItem>> getListRawListSingleLiveEvent() {
        return listRawListSingleLiveEvent;
    }

    public SingleLiveEvent<String> getStringErrorSingleLiveEvent() {
        return stringErrorSingleLiveEvent;
    }

    private void insertInServiceItemMap(Map<String,List<ServiceItemType>> listHashMap,String serviceType, ServiceItemType itemType) {
        if (listHashMap.containsKey(serviceType)) {
            List<ServiceItemType> serviceItemTypeList=listHashMap.get(serviceType);
            for(ServiceItemType serviceItemType:serviceItemTypeList){
                    if(stringServiceItemTypeMap.containsKey(serviceItemType.getScannedItemType())) {
                        ServiceItemType serviceItemType1 = stringServiceItemTypeMap.get(serviceItemType.getScannedItemType());
                        serviceItemType1.setQtyPurchased(String.valueOf(Double.valueOf(serviceItemType.getQtyPurchased()) + 1.0));
                        serviceItemType1.setProductPrice(String.valueOf(Double.valueOf(serviceItemType1.getProductPrice()) + Double.valueOf(serviceItemType.getProductPrice())));
                        stringServiceItemTypeMap.put(serviceItemType.getScannedItemType(), serviceItemType1);
                    }else {
                        stringServiceItemTypeMap.put(serviceItemType.getScannedItemType(), serviceItemType);
                    }
             for(int i=0;i<listHashMap.get(serviceType).size();i++){
                 listHashMap.get(serviceType).set(i,stringServiceItemTypeMap.get(listHashMap.get(serviceType).get(i).getScannedItemType()));
             }
            }
        } else {
            List<ServiceItemType> serviceItemTypeList = new ArrayList<>();
            serviceItemTypeList.add(itemType);
            listHashMap.put(serviceType, serviceItemTypeList);
            stringServiceItemTypeMap.put(itemType.getScannedItemType(),itemType);
        }
    }

}
