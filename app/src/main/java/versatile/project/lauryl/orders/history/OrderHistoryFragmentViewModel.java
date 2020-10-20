package versatile.project.lauryl.orders.history;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonObject;

import java.util.List;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.orders.history.model.ServiceType;

public class OrderHistoryFragmentViewModel extends BaseViewModel {
    private OrderHistoryRepository orderHistoryRepository;
    public OrderHistoryFragmentViewModel(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public void getOrderItems(String accessToken, JsonObject request){
        orderHistoryRepository.getOrderItems(accessToken,request);
    }
    public LiveData<List<ServiceType>> onOrderItemReceived(){
        return orderHistoryRepository.getListServiceSkuTypeSingleLiveEvent();
    }
}
