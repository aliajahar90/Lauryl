package versatile.project.lauryl.orders.create;

import org.joda.time.DateTime;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.orders.OrderRepository;
import versatile.project.lauryl.orders.create.model.CreateOrderData;

public class CreateOrderViewModel extends BaseViewModel {
    OrderRepository createOrderRepository;
    public CreateOrderViewModel() {
        this.createOrderRepository = new OrderRepository();
    }
   public void createOrderOnServerWithoutPayment(String accessToken,CreateOrderData createOrderReq){
        createOrderRepository.createOrder(accessToken,createOrderReq);
    }

//    CreateOrderData getCreateOrderParams(CreateOrderData.Details createOrderReq){
//        CreateOrderData createOrderData=new CreateOrderData();
//        CreateOrderData.Details creatOrderDetails=createOrderReq;
//        creatOrderDetails.setOrderNumber(getCurrentDateTime());
//        //keeping static as of now
//        creatOrderDetails.setOrderTotal("100");
//        creatOrderDetails.setVAccountId("1");
//        creatOrderDetails.setMarketPlaceName(AllConstants.Orders.MARKET_PLACE_NAME);
//        creatOrderDetails.setOrderDateTime(getCurrentDateTime());
//        creatOrderDetails.setPaymentDateTime(getCurrentDateTime());
//        createOrderData.setDetails(creatOrderDetails);
//        return createOrderData;
//    }

    String getCurrentDateTime(){
        DateTime dateTime=DateTime.now();
        return String.valueOf(dateTime.getMillis());
    }

    public SingleLiveEvent<String> getCreateOrderSuccessEvent(){
       return createOrderRepository.getCreateOrderSuccessEvent();
    }
    public SingleLiveEvent<String> getCreateOrderFailedEvent(){
        return createOrderRepository.getCreateOrderFailedEvent();
    }
}
