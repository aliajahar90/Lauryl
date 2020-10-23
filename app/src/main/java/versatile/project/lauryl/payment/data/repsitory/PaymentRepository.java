package versatile.project.lauryl.payment.data.repsitory;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Razorpay;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.razorpay.ValidateVpaCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.base.asyncjob.TaskRunner;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.payment.backgroundjob.PaymentBackgroundTask;
import versatile.project.lauryl.payment.data.NetBanking;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.viewModel.Signature;
import versatile.project.lauryl.utils.AllConstants;

import static versatile.project.lauryl.utils.AllConstants.Payment.HotBanks.BARB_R;
import static versatile.project.lauryl.utils.AllConstants.Payment.HotBanks.CNRB;
import static versatile.project.lauryl.utils.AllConstants.Payment.HotBanks.HDFC;
import static versatile.project.lauryl.utils.AllConstants.Payment.HotBanks.ICIC;
import static versatile.project.lauryl.utils.AllConstants.Payment.HotBanks.KKBK;
import static versatile.project.lauryl.utils.AllConstants.Payment.HotBanks.SBIN;

public class PaymentRepository extends LaurylRepository {
    // static variable single_instance of type Singleton
    private static PaymentRepository single_instance = null;
    private Razorpay razorpay;
    private SingleLiveEvent<Boolean> isCardSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isUpiSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingSBINSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingHDFCSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingCANARASupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingICICSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingBBSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isNetBankingKotakSupported = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> isValidVpa = new SingleLiveEvent<>();
    private SingleLiveEvent<String> errorValidateVPA = new SingleLiveEvent<>();
    private SingleLiveEvent<List<NetBanking>> netBankList = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> onSwitchToWebCheckout = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> onSwitchDefaultView= new SingleLiveEvent<>();
    private SingleLiveEvent<String> paymentMethodLoadError = new SingleLiveEvent<>();
    private SingleLiveEvent<PaymentBaseShareData.PaymentSuccess> paymentSuccessSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<PaymentBaseShareData.PaymentError> paymentErrorSingleLiveEvent = new SingleLiveEvent<>();

    public void setRazorpay(Razorpay razorpay) {
        this.razorpay = razorpay;
    }

    private PaymentRepository(Razorpay razorpay) {
        this.razorpay = razorpay;
    }

    // static method to create instance of Singleton class
    public static PaymentRepository getInstance(Razorpay razorpay) {
        if (single_instance == null)
            single_instance = new PaymentRepository(razorpay);

        return single_instance;
    }

    public void getPaymentMethods() {
        razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
            @Override
            public void onPaymentMethodsReceived(String result) {
                Log.d("Result", "" + result);
                inflateLists(result);
                onSwitchDefaultView.setValue(true);
            }

            @Override
            public void onError(String error) {
                Log.d("Get Payment error", error);
                paymentMethodLoadError.setValue(error);
                onSwitchDefaultView.setValue(true);
            }
        });
    }

    private void inflateLists(String result) {
        try {
            JSONObject paymentMethods = new JSONObject(result);
            JSONObject banksListJSON = paymentMethods.getJSONObject(AllConstants.Payment.NET_BANKING);
            if (paymentMethods.get(AllConstants.Payment.CARD) instanceof Boolean && paymentMethods.get(AllConstants.Payment.DEBIT_CARD) instanceof Boolean && paymentMethods.get(AllConstants.Payment.CREDIT_CARD) instanceof Boolean) {
                if (paymentMethods.getBoolean(AllConstants.Payment.CARD) || (paymentMethods.getBoolean(AllConstants.Payment.DEBIT_CARD) && paymentMethods.getBoolean(AllConstants.Payment.CREDIT_CARD))) {
                    isCardSupported.setValue(true);
                }
            }
            if (paymentMethods.get(AllConstants.Payment.UPI) instanceof Boolean) {
                if (paymentMethods.getBoolean(AllConstants.Payment.UPI)) {
                    isUpiSupported.setValue(true);
                }
            }
            if (banksListJSON != null) {
                isNetBankingSupported.setValue(true);
            }
            List<NetBanking> netBankingList = new ArrayList<>();
            Iterator<String> itr1 = banksListJSON.keys();
            while (itr1.hasNext()) {
                String key = itr1.next();
                try {
                    checkHotBanks(key);
                } catch (IllegalStateException ile) {
                    Log.d("Unknown bank codes", "" + ile.getMessage());
                }
                NetBanking netBanking = new NetBanking();
                netBanking.setBankCode(key);
                netBanking.setBankLogo(getBankLogoUrls(key));
                try {
                    netBanking.setBankName(banksListJSON.getString(key));
                    netBankingList.add(netBanking);
                    netBankList.setValue(netBankingList);
                } catch (JSONException e) {
                    Log.d("Reading Banks List", "" + e.getMessage());
                    paymentMethodLoadError.setValue(AllConstants.Payment.Errors.ERROR_LOAD_PAYMENT);
                }
            }

        } catch (Exception e) {
            Log.e("Parsing Result", "" + e.getMessage());
            paymentMethodLoadError.setValue(AllConstants.Payment.Errors.ERROR_LOAD_PAYMENT);
        }
    }

    void checkHotBanks(String key) {
        switch (key) {
            case SBIN:
                isNetBankingSBINSupported.setValue(true);
                break;
            case CNRB:
                isNetBankingCANARASupported.setValue(true);
                break;
            case HDFC:
                isNetBankingHDFCSupported.setValue(true);
                break;
            case ICIC:
                isNetBankingICICSupported.setValue(true);
                break;
            case KKBK:
                isNetBankingKotakSupported.setValue(true);
                break;
            case BARB_R:
                isNetBankingBBSupported.setValue(true);
                break;
            default:
        }
    }
    String getBankLogoUrls(String bankCode){
        return razorpay.getBankLogoUrl(bankCode);
    }

    public SingleLiveEvent<Boolean> getIsCardSupported() {
        return isCardSupported;
    }

    public SingleLiveEvent<Boolean> getIsUpiSupported() {
        return isUpiSupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingSupported() {
        return isNetBankingSupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingSBINSupported() {
        return isNetBankingSBINSupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingHDFCSupported() {
        return isNetBankingHDFCSupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingCANARASupported() {
        return isNetBankingCANARASupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingICICSupported() {
        return isNetBankingICICSupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingBBSupported() {
        return isNetBankingBBSupported;
    }

    public SingleLiveEvent<Boolean> getIsNetBankingKotakSupported() {
        return isNetBankingKotakSupported;
    }

    public SingleLiveEvent<List<NetBanking>> getNetBankList() {
        return netBankList;
    }

    public SingleLiveEvent<Boolean> getIsValidVpa() {
        return isValidVpa;
    }

    public SingleLiveEvent<String> getErrorValidateVPA() {
        return errorValidateVPA;
    }

    public void validateVPA(String vpa) {
        razorpay.isValidVpa(vpa, new ValidateVpaCallback() {
            @Override
            public void onResponse(boolean b) {
                isValidVpa.setValue(b);
            }

            @Override
            public void onFailure() {
                Log.d("VPA", "Error in validating");
                errorValidateVPA.setValue(AllConstants.Payment.Errors.ERROR_VPA_VALIDATION);
            }
        });
    }

    public void processPayment(JSONObject payload) {
        createOrderInServer(payload);

    }

    public SingleLiveEvent<Boolean> getOnSwitchToWebCheckout() {
        return onSwitchToWebCheckout;
    }

    public SingleLiveEvent<Boolean> getOnSwitchDefaultView() {
        return onSwitchDefaultView;
    }

    public void createOrderInServer(JSONObject payload) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient("rzp_live_uCuH3yqyHZjoYj", "V1bFCk7Jurwyjm74cCO8KHHP");
            TaskRunner paymentTaskRunner = new TaskRunner();
            paymentTaskRunner.executeAsync(new PaymentBackgroundTask(new PaymentBackgroundTask.iOnDataFetched() {
                @Override
                public void notifyPreProcess() {

                }

                @Override
                public void onBackgroundResult(Object result) {
                    try {
                        payload.put("order_id", result instanceof Order ? ((Order) result).get("id") : "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    razorpay.validateFields(payload, new Razorpay.ValidationListener() {
                        @Override
                        public void onValidationSuccess() {
                            try {
                                Log.d("Payment", "validation success");
                                onSwitchToWebCheckout.setValue(true);
                                razorpay.submit(payload, new PaymentResultWithDataListener() {
                                    @Override
                                    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
                                        Log.d("Payment", "success" + razorpayPaymentId);
                                        onSwitchDefaultView.setValue(true);
                                        PaymentBaseShareData.PaymentSuccess paymentSuccess = new PaymentBaseShareData.PaymentSuccess();
                                        paymentSuccess.setPaymentTransactionId(razorpayPaymentId);
                                        paymentSuccess.setPaymentData(paymentData);
                                        paymentSuccessSingleLiveEvent.setValue(paymentSuccess);
                                      //  verifyPostPaymentSignature(result instanceof Order ? ((Order) result).get("id"):"",paymentData,"V1bFCk7Jurwyjm74cCO8KHHP",razorpayPaymentId);
                                    }

                                    @Override
                                    public void onPaymentError(int i, String description, PaymentData paymentData) {
                                        onSwitchDefaultView.setValue(true);
                                        PaymentBaseShareData.PaymentError paymentError=new PaymentBaseShareData.PaymentError();
                                        paymentError.setCode(i);
                                        paymentError.setDescription(description);
                                        paymentError.setPaymentData(paymentData);
                                        paymentErrorSingleLiveEvent.setValue(paymentError);
                                        Log.d("Payment", "failed" + description);
                                    }
                                });
                            } catch (Exception e) {
                                onSwitchDefaultView.setValue(true);
                                Log.e("com.example", "Exception: ", e);
                            }
                        }

                        @Override
                        public void onValidationError(Map<String, String> error) {
                            onSwitchDefaultView.setValue(true);
                            Log.d("Payment", "validation failed");
                            // Log.d("com.example", "Validation failed: " + error.get("field") + " " + error.get("description"));
                            // Toast.makeText(PaymentOptions.this, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void notifyPostProcess() {

                }

                @Override
                public Object notifyDoBackground() {
                    JSONObject options = new JSONObject();
                    try {
                        options.put("amount", 100);
                        options.put("currency", "INR");
                        Order order = razorpayClient.Orders.create(options);
                        return order;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (RazorpayException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }));
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
    }

//    void verifyPostPaymentSignature(String orderId,PaymentData paymentData, String secretKey,String razorPayPaymentId){
//       // Utils.verifyPaymentSignature()
//        if( Signature.verifyPaymentSignature(orderId,paymentData.getPaymentId(),secretKey,paymentData.getSignature())){
//                PaymentBaseShareData.PaymentSuccess paymentSuccess=new PaymentBaseShareData.PaymentSuccess();
//                paymentSuccess.setPaymentTransactionId(razorPayPaymentId);
//                paymentSuccess.setPaymentData(paymentData);
//                paymentSuccessSingleLiveEvent.setValue(paymentSuccess);
//        }else {
//            PaymentBaseShareData.PaymentError paymentError=new PaymentBaseShareData.PaymentError();
//            paymentError.setCode(-1);
//            paymentError.setDescription(null);
//            paymentError.setPaymentData(paymentData);
//            paymentErrorSingleLiveEvent.setValue(paymentError);
//        }
//    }

    public SingleLiveEvent<String> getPaymentMethodLoadError() {
        return paymentMethodLoadError;
    }

    public SingleLiveEvent<PaymentBaseShareData.PaymentSuccess> getPaymentSuccessSingleLiveEvent() {
        return paymentSuccessSingleLiveEvent;
    }

    public SingleLiveEvent<PaymentBaseShareData.PaymentError> getPaymentErrorSingleLiveEvent() {
        return paymentErrorSingleLiveEvent;
    }
}
