package versatile.project.lauryl.orders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.model.BooleanResponse;
import versatile.project.lauryl.orders.create.model.CreateOrderData;
import versatile.project.lauryl.orders.create.model.CreateOrderResponse;
import versatile.project.lauryl.utils.AllConstants;

public class OrderRepository extends LaurylRepository {

    private SingleLiveEvent<String> createOrderSuccessEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<String> createOrderFailedEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<BooleanResponse> cancelOrderSuccessEvent=new SingleLiveEvent<>();
    private SingleLiveEvent<String> cancelOrderErrorEvent=new SingleLiveEvent<>();
   public void createOrder(String accessToken,CreateOrderData createOrderData){
        String createOrderJson= new Gson().toJson(createOrderData);
        JsonObject jsonObject=new JsonParser().parse(createOrderJson).getAsJsonObject();
         getApiVersatileServices().createOrder(accessToken,jsonObject).enqueue(new Callback<CreateOrderResponse>() {
             @Override
             public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                 if(response!=null && response.isSuccessful() && response.code()==200 && response.body()!=null){
                     createOrderSuccessEvent.setValue(response.body().getStatus());
                 }else {
                     createOrderFailedEvent.setValue("true");
                 }
             }

             @Override
             public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                 createOrderFailedEvent.setValue("true");
             }
         });
    }

    public void cancelOrder(String accessToken, JsonObject cancelOrderJson){
       getApiVersatileServices().cancelOrder(accessToken,cancelOrderJson).enqueue(new Callback<BooleanResponse>() {
           @Override
           public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
               if(response!=null && response.isSuccessful()){
                   cancelOrderSuccessEvent.postValue(response.body());
               }
               else {
                   cancelOrderErrorEvent.postValue(AllConstants.Orders.Errors.ERROR_API_FAILED);
               }
           }

           @Override
           public void onFailure(Call<BooleanResponse> call, Throwable t) {
                cancelOrderErrorEvent.postValue(AllConstants.Orders.Errors.ERROR_API_FAILED);
           }
       });

    }

    public SingleLiveEvent<String> getCreateOrderSuccessEvent() {
        return createOrderSuccessEvent;
    }

    public SingleLiveEvent<String> getCreateOrderFailedEvent() {
        return createOrderFailedEvent;
    }

    public SingleLiveEvent<BooleanResponse> getCancelOrderSuccessEvent() {
        return cancelOrderSuccessEvent;
    }

    public SingleLiveEvent<String> getCancelOrderErrorEvent() {
        return cancelOrderErrorEvent;
    }
}
