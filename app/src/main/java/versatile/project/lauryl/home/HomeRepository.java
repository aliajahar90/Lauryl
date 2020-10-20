package versatile.project.lauryl.home;

import android.text.TextUtils;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.model.MyOrdersDataItem;
import versatile.project.lauryl.model.MyOrdersResponse;
import versatile.project.lauryl.utils.AllConstants;

public class HomeRepository  extends LaurylRepository {
    SingleLiveEvent<List<MyOrdersDataItem>> listAwatingPickupSingleLiveEvent=new SingleLiveEvent<>();
    SingleLiveEvent<List<MyOrdersDataItem>> listAwatingDeliverySingleLiveEvent=new SingleLiveEvent<>();
    SingleLiveEvent<List<MyOrdersDataItem>> listCompletedDeliverySingleLiveEvent=new SingleLiveEvent<>();
    SingleLiveEvent<String> orderFetchErrorSingleLiveEvent=new SingleLiveEvent<>();
    public void getHomeOrders(String accessToken, JsonObject requestObject){
        getApiVersatileServices().getMyOrders(accessToken,requestObject).enqueue(new Callback<MyOrdersResponse>() {
            @Override
            public void onResponse(Call<MyOrdersResponse> call, Response<MyOrdersResponse> response) {
                if(response!=null && response.isSuccessful() && response.code()==200){
                    if(response.body()!=null && response.body().getData()!=null && !(response.body().getData().getList().isEmpty())){
                        listAwatingPickupSingleLiveEvent.setValue(getAwaitingPickupList(response.body()));
                        listAwatingDeliverySingleLiveEvent.setValue(getAwaitingDeliveryList(response.body()));
                        listCompletedDeliverySingleLiveEvent.setValue(getCompletedDeliveryList(response.body()));
                    }else {
                        orderFetchErrorSingleLiveEvent.setValue("");
                    }
                }else {
                    orderFetchErrorSingleLiveEvent.setValue("");
                }
            }

            @Override
            public void onFailure(Call<MyOrdersResponse> call, Throwable t) {
                orderFetchErrorSingleLiveEvent.setValue(t.getMessage());
            }
        });
    }

    List<MyOrdersDataItem> getAwaitingPickupList(MyOrdersResponse response){
        List<MyOrdersDataItem> awatingPickupList=new ArrayList<>();
        for(MyOrdersDataItem myOrdersDataItem:response.getData().getList()){
            if(TextUtils.equals(myOrdersDataItem.getOrderStage(), AllConstants.Orders.OrderStage.Awaiting_Pickup))
            {
                awatingPickupList.add(myOrdersDataItem);
                return awatingPickupList;
            }
        }
        return awatingPickupList;
    }
    List<MyOrdersDataItem> getAwaitingDeliveryList(MyOrdersResponse response){
        for(MyOrdersDataItem myOrdersDataItem:response.getData().getList()){
            if(TextUtils.equals(myOrdersDataItem.getOrderStage(), AllConstants.Orders.OrderStage.Awaiting_Delivery))
            {
                List<MyOrdersDataItem> awaitingDeliveryList=new ArrayList<>();
                awaitingDeliveryList.add(myOrdersDataItem);
                return awaitingDeliveryList;
            }
        }
        return new ArrayList<>();
    }
    List<MyOrdersDataItem> getCompletedDeliveryList(MyOrdersResponse response){
        for(MyOrdersDataItem myOrdersDataItem:response.getData().getList()){
            if(TextUtils.equals(myOrdersDataItem.getOrderStage(), AllConstants.Orders.OrderStage.Completed_Delivery))
            {
                List<MyOrdersDataItem> completedList=new ArrayList<>();
                completedList.add(myOrdersDataItem);
                return completedList;
            }
        }
        return new ArrayList<>();
    }

    public SingleLiveEvent<List<MyOrdersDataItem>> getListAwatingPickupSingleLiveEvent() {
        return listAwatingPickupSingleLiveEvent;
    }

    public SingleLiveEvent<List<MyOrdersDataItem>> getListAwatingDeliverySingleLiveEvent() {
        return listAwatingDeliverySingleLiveEvent;
    }

    public SingleLiveEvent<List<MyOrdersDataItem>> getListCompletedDeliverySingleLiveEvent() {
        return listCompletedDeliverySingleLiveEvent;
    }

    public SingleLiveEvent<String> getOrderFetchErrorSingleLiveEvent() {
        return orderFetchErrorSingleLiveEvent;
    }
}
