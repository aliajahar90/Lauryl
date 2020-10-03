package versatile.project.lauryl.payment.data.repsitory;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Razorpay;
import com.razorpay.ValidateVpaCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.payment.data.NetBanking;
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
    private MutableLiveData<Boolean> isCardSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUpiSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingSBINSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingHDFCSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingCANARASupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingICICSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingBBSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNetBankingKotakSupported = new MutableLiveData<>();
    private MutableLiveData<Boolean> isValidVpa = new MutableLiveData<>();
    private MutableLiveData<String> errorValidateVPA = new MutableLiveData<>();
    private MutableLiveData<List<NetBanking>> netBankList = new MutableLiveData<>();
    private MutableLiveData<Integer> onSwitchView = new MutableLiveData<>();

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
            }

            @Override
            public void onError(String error) {
                Log.d("Get Payment error", error);
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
                try {
                    netBanking.setBankName(banksListJSON.getString(key));
                    netBankingList.add(netBanking);
                    netBankList.setValue(netBankingList);
                } catch (JSONException e) {
                    Log.d("Reading Banks List", "" + e.getMessage());
                }
            }

        } catch (Exception e) {
            Log.e("Parsing Result", "" + e.getMessage());
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

    public MutableLiveData<Boolean> getIsCardSupported() {
        return isCardSupported;
    }

    public MutableLiveData<Boolean> getIsUpiSupported() {
        return isUpiSupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingSupported() {
        return isNetBankingSupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingSBINSupported() {
        return isNetBankingSBINSupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingHDFCSupported() {
        return isNetBankingHDFCSupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingCANARASupported() {
        return isNetBankingCANARASupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingICICSupported() {
        return isNetBankingICICSupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingBBSupported() {
        return isNetBankingBBSupported;
    }

    public MutableLiveData<Boolean> getIsNetBankingKotakSupported() {
        return isNetBankingKotakSupported;
    }

    public MutableLiveData<List<NetBanking>> getNetBankList() {
        return netBankList;
    }

    public MutableLiveData<Boolean> getIsValidVpa() {
        return isValidVpa;
    }

    public MutableLiveData<String> getErrorValidateVPA() {
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
        razorpay.validateFields(payload, new Razorpay.ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    Log.d("Payment","validation success");
                    onSwitchView.setValue(AllConstants.Payment.PaymentViewCheckout);
                    razorpay.submit(payload, new PaymentResultWithDataListener() {
                        @Override
                        public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
                            Log.d("Payment","success"+razorpayPaymentId);
                            onSwitchView.setValue(AllConstants.Payment.PaymentViewOptions);
                        }

                        @Override
                        public void onPaymentError(int i, String description, PaymentData paymentData) {
                            onSwitchView.setValue(AllConstants.Payment.PaymentViewOptions);
                            Log.d("Payment","failed"+description);
                        }
                    });
                } catch (Exception e) {
                    Log.e("com.example", "Exception: ", e);
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                onSwitchView.setValue(AllConstants.Payment.PaymentViewOptions);
                Log.d("Payment","validation failed");
                // Log.d("com.example", "Validation failed: " + error.get("field") + " " + error.get("description"));
                // Toast.makeText(PaymentOptions.this, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<Integer> getOnSwitchView() {
        return onSwitchView;
    }
}
