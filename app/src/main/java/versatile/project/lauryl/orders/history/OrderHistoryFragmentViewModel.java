package versatile.project.lauryl.orders.history;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;

import java.util.List;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.orders.history.model.ServiceType;

public class OrderHistoryFragmentViewModel extends BaseViewModel {
    private OrderHistoryRepository orderHistoryRepository;
    public OrderHistoryFragmentViewModel() {
        this.orderHistoryRepository = new OrderHistoryRepository();
    }

    public void getOrderItems(String accessToken, JsonObject request){
        orderHistoryRepository.getOrderItems(accessToken,request);
    }
    public SingleLiveEvent<List<ServiceType>> onOrderItemReceived(){
        return orderHistoryRepository.getListServiceSkuTypeSingleLiveEvent();
    }
    public SingleLiveEvent<String> onItemError(){
        return orderHistoryRepository.getStringErrorSingleLiveEvent();
    }
}
