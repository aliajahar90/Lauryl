package versatile.project.lauryl.payment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import timber.log.Timber;
import versatile.project.lauryl.application.MyApplication;
import versatile.project.lauryl.model.MyOrdersDataItem;
import versatile.project.lauryl.orders.create.model.CreateOrderData;
import versatile.project.lauryl.orders.reschedule.ReSchedulePckUpFragment;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.pickup.data.PickUpSharedData;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentPayNowFragment extends PaymentFragment {

    MyOrdersDataItem myOrdersDataItem;
    Gson mGson;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGson=new Gson();
        try {
            myOrdersDataItem =mGson.fromJson((String) getArguments().get(AllConstants.PickUp.PickUpData), MyOrdersDataItem.class);
        }
        catch (Exception e){
            Timber.d(ReSchedulePckUpFragment.class.getName(),"data not loaded");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void doSubmitValidation() {
        switch (activePaymentType) {
            case PaymentTypeUpi:
                processUpiService();
                break;
            case PaymentTypeCards:
                processCardPaymentService();
                break;
            case PaymentTypeNetBanking:
                processNetBankPaymentService();
                break;
        }
    }

    @Override
    protected void createMyOrder(PaymentBaseShareData.PaymentSuccess paymentSuccess) {
        myOrdersDataItem.setTransactionId(paymentSuccess.getPaymentTransactionId());
        myOrdersDataItem.setRazorPayOrderId(paymentSuccess.getRazorOrderId());
        myOrdersDataItem.setPaymentReceived(true);
        JsonObject jsonElement = new JsonParser().parse(new Gson().toJson(myOrdersDataItem)).getAsJsonObject();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("orderNumber", myOrdersDataItem.getOrderNumber());
        jsonObject.addProperty("orderStage", myOrdersDataItem.getOrderStage());
        jsonObject.addProperty("transactionId", paymentSuccess.getPaymentTransactionId());
        jsonObject.addProperty("razorPayOrderId", paymentSuccess.getRazorOrderId());
        CreateOrderData createOrderData=new CreateOrderData();
        CreateOrderData.Details details=new CreateOrderData.Details();
        //keeping static as of now
        details.setOrderNumber(myOrdersDataItem.getOrderNumber());
        details.setOrderTotal(myOrdersDataItem.getOrderTotal());
        details.setVAccountId(myOrdersDataItem.getVAccountId());
        details.setMarketPlaceName(myOrdersDataItem.getMarketPlaceName());
        details.setOrderDateTime(""+myOrdersDataItem.getOrderDateTime());
        details.setPaymentDateTime(""+myOrdersDataItem.getPaymentDateTime());
        details.setPaymentReceived(myOrdersDataItem.getPaymentReceived());
        details.setShippingAddress1(myOrdersDataItem.getShippingAddress1());
        details.setShippingAddress2(myOrdersDataItem.getShippingAddress2());
        details.setShippingAddress3(myOrdersDataItem.getShippingAddress3());
        details.setShippingCity(myOrdersDataItem.getShippingCity());
        details.setShippingState(myOrdersDataItem.getShippingState());
        details.setShippingCountry(myOrdersDataItem.getShippingCountry());
        details.setPickupAddress1(myOrdersDataItem.getShippingAddress1());
        details.setPickupAddress2(myOrdersDataItem.getShippingAddress2());
        details.setPickupAddress3(myOrdersDataItem.getShippingAddress3());
        details.setPickupCountryCode(myOrdersDataItem.getShippingCountryCode());
        details.setPickupCity(myOrdersDataItem.getShippingCity());
        details.setPickupState(myOrdersDataItem.getShippingState());
        details.setPickupCountry(myOrdersDataItem.getShippingCountry());
        details.setShippingPostCode(myOrdersDataItem.getShippingPostCode());
        details.setTransactionId(myOrdersDataItem.getTransactionId());
        details.setRazorPayOrderId(myOrdersDataItem.getRazorPayOrderId());
        details.setServiceList(myOrdersDataItem.getServiceList());
        details.setPhoneNumber(myOrdersDataItem.getPhoneNumber());
        details.setOrderStage(myOrdersDataItem.getOrderStage());
        details.setEmailId(myOrdersDataItem.getEmailId());
        details.setSpecialInstructions(myOrdersDataItem.getSpecialInstructions());
        details.setPickupDate(myOrdersDataItem.getPickupDate());
        details.setPickupSlot(myOrdersDataItem.getPickupSlot());
        details.setLatitude(myOrdersDataItem.getLatitude());
        details.setLongitude(myOrdersDataItem.getLongitude());
        details.setBuyerName(myOrdersDataItem.getBuyerName());
        details.setReSchedule(false);
        createOrderData.setDetails(details);
        capturePaymentSuccessRequiredData(paymentSuccess,createOrderData);
        paymentViewModel.modifyOrderPayNow(((MyApplication) getActivity().getApplicationContext()).getAccessToken(), jsonObject);


    }
}
