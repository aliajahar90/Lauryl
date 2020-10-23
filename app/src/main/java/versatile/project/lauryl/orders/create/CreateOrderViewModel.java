package versatile.project.lauryl.orders.create;

import org.joda.time.DateTime;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.orders.create.model.CreateOrderData;
import versatile.project.lauryl.utils.AllConstants;

public class CreateOrderViewModel extends BaseViewModel {
    CreateOrderRepository createOrderRepository;
    public CreateOrderViewModel() {
        this.createOrderRepository = new CreateOrderRepository();
    }
   public void createOrderOnServerWithoutPayment(String accessToken,CreateOrderData.Details createOrderReq){
        createOrderRepository.createOrder(accessToken,getCreateOrderParams(createOrderReq));
    }

    CreateOrderData getCreateOrderParams(CreateOrderData.Details createOrderReq){
        CreateOrderData createOrderData=new CreateOrderData();
        CreateOrderData.Details creatOrderDetails=createOrderReq;
        creatOrderDetails.setOrderNumber(getCurrentDateTime());
        //keeping static as of now
        creatOrderDetails.setOrderTotal("100");
        creatOrderDetails.setVAccountId("1");
        creatOrderDetails.setMarketPlaceName(AllConstants.Orders.MARKET_PLACE_NAME);
        creatOrderDetails.setOrderDateTime(getCurrentDateTime());
        creatOrderDetails.setPaymentDateTime(getCurrentDateTime());
        creatOrderDetails.setPaymentReceived(false);
        creatOrderDetails.setShippingAddress1("");
        creatOrderDetails.setShippingAddress2("");
        creatOrderDetails.setShippingAddress3("");
        creatOrderDetails.setShippingCity("");
        creatOrderDetails.setShippingState("");
        creatOrderDetails.setShippingCountry("");
        creatOrderDetails.setPickupAddress1("");
        creatOrderDetails.setPickupAddress2("");
        creatOrderDetails.setShippingAddress3("");
        creatOrderDetails.setPickupCountryCode("");
        createOrderData.setDetails(creatOrderDetails);
        return createOrderData;
    }

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
