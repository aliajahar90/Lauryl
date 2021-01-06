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
import versatile.project.lauryl.model.MyOrdersDataItem;
import versatile.project.lauryl.orders.history.model.OrderItemsResponse;
import versatile.project.lauryl.orders.history.model.ServiceItemType;
import versatile.project.lauryl.orders.history.model.ServiceType;
import versatile.project.lauryl.profile.ProfileRepository;
import versatile.project.lauryl.utils.AllConstants;

public class OrderHistoryRepository  extends LaurylRepository {
private SingleLiveEvent<List<ServiceType>> listServiceSkuTypeSingleLiveEvent= new SingleLiveEvent<>();
private SingleLiveEvent<List<OrderItemsResponse.ServiceItem>> listRawListSingleLiveEvent= new SingleLiveEvent<>();
private SingleLiveEvent<String> stringErrorSingleLiveEvent=new SingleLiveEvent<>();

    void getOrderItems(String accessToken, JsonObject orderHistoryRequest, MyOrdersDataItem myOrdersDataItem){
        getApiVersatileServices().getOrderItems(accessToken,orderHistoryRequest).enqueue(new Callback<OrderItemsResponse>() {
            @Override
            public void onResponse(Call<OrderItemsResponse> call, Response<OrderItemsResponse> response) {
                if(response!=null && response.isSuccessful() && response.code()==200){
                    if(response.body()!=null && response.body().getData()!=null && !(response.body().getData().getList().isEmpty())){
                        listServiceSkuTypeSingleLiveEvent.setValue(processHistory(response.body().getData().getList(),myOrdersDataItem));
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

    List<ServiceType> processHistory(List<OrderItemsResponse.ServiceItem> serviceItems, MyOrdersDataItem ordersDataItem){
        List<String> headers =new ArrayList<>();
        List<String> scannedItems =new ArrayList<>();
        List<ServiceType> serviceTypeList=new ArrayList<>();
        Map<String,String> ServiceNameMap=new HashMap<>();
        for(OrderItemsResponse.ServiceItem item:serviceItems){
            headers.add(item.getEan());
            String pName=getServiceNameWithWeight(item.getEan(),item.getProductName(),ordersDataItem);
            pName=getServiceNameWithPrice(item.getEan(),pName,ordersDataItem);
            ServiceNameMap.put(item.getEan(),pName);
            scannedItems.add(item.getScannedItemType());
        }
        Set<String> uniqueEAN =new LinkedHashSet<>(headers);
       // Map<String, List<OrderItemsResponse.ServiceItem>> serviceMap=new HashMap<>();
        for(String s:uniqueEAN){
            //serviceMap.put(s,getMyList(s,serviceItems));
            serviceTypeList.add(new ServiceType(ServiceNameMap.get(s),getMyItems(processList(getMyList(s,serviceItems)))));
        }
        return serviceTypeList;
    }

    List<OrderItemsResponse.ServiceItem> getMyList(String ser,List<OrderItemsResponse.ServiceItem> serviceItems ){
        List<OrderItemsResponse.ServiceItem> myList=new ArrayList<>();
        for(OrderItemsResponse.ServiceItem item:serviceItems){
            if(TextUtils.equals(item.getEan(),ser)){
                myList.add(item);
            }
        }
        return myList;
    }

    Map<String, ServiceItemType> processList(List<OrderItemsResponse.ServiceItem> serviceItems){
        Map<String, ServiceItemType> itemInside=new HashMap<>();
        for(OrderItemsResponse.ServiceItem serviceItem:serviceItems){
            if(itemInside.containsKey(serviceItem.getScannedItemType())){
                ServiceItemType serviceItemType1 = itemInside.get(serviceItem.getScannedItemType());
                serviceItemType1.setQtyPurchased(String.valueOf(Double.valueOf(serviceItem.getQtyPurchased()) + 1.0));
                serviceItemType1.setProductPrice(String.valueOf(Double.valueOf(serviceItemType1.getProductPrice()) + Double.valueOf(serviceItem.getProductPrice())));
                itemInside.put(serviceItem.getScannedItemType(),serviceItemType1);

            }else {
                itemInside.put(serviceItem.getScannedItemType(),new ServiceItemType(serviceItem.getScannedItemType(),serviceItem.getQtyPurchased(),serviceItem.getProductPrice()));
            }
        }
        return itemInside;
    }

    List<ServiceItemType> getMyItems(Map<String, ServiceItemType> itemInside){
        List<ServiceItemType> toGive=new ArrayList<>();
        for(Map.Entry<String, ServiceItemType> entry: itemInside.entrySet()){
            toGive.add(entry.getValue());
        }
        return toGive;
    }

    String getServiceNameWithWeight(String ean,String productName,MyOrdersDataItem myOrdersDataItem){
        String pName=productName;
        if(myOrdersDataItem.getWeightList()!=null) {
            for (String weightString : myOrdersDataItem.getWeightList()) {
                if (weightString.contains(ean)) {
                    String[] arrOfStr = weightString.split("/");
                    if (arrOfStr[1] != null && !TextUtils.isEmpty(arrOfStr[1])) {
                        pName = pName + "(" + arrOfStr[1] + "Kg" + ")";
                    }
                }
            }
        }
        return pName;
    }
    String getServiceNameWithPrice(String ean,String productName,MyOrdersDataItem myOrdersDataItem){
        String pName=productName;
        if(myOrdersDataItem.getServicePrices()!=null) {
            for (String weightString : myOrdersDataItem.getServicePrices()) {
                if (weightString.contains(ean)) {
                    String[] arrOfStr = weightString.split("/");
                    if (arrOfStr[1] != null && !TextUtils.isEmpty(arrOfStr[1])) {
                        pName = pName + "(" + "\u20B9" + arrOfStr[1] + ")";
                    }
                }
            }
        }
        return pName;
    }
}
