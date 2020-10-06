package versatile.project.lauryl.payment.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.razorpay.Constants;
import com.razorpay.Razorpay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.payment.data.NetBanking;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.data.repsitory.PaymentRepository;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentViewModel extends BaseViewModel {
    private Razorpay razorpay;
    PaymentRepository paymentRepository;
    MutableLiveData<String> cardPaymentValidationError=new MutableLiveData<>();
    MutableLiveData<Boolean> cardPaymentValidationSuccess=new MutableLiveData<>();

    public PaymentViewModel(Razorpay razorpay) {
        this.razorpay = razorpay;
        this.paymentRepository = PaymentRepository.getInstance(razorpay);
    }
    public void setRazorpay(Razorpay razorpay) {
        this.razorpay = razorpay;
        paymentRepository.setRazorpay(this.razorpay);
    }
    public void getValidPaymentMethods() {
        paymentRepository.getPaymentMethods();
    }

    public LiveData<Boolean> isUpiPaymentSupported() {
        return paymentRepository.getIsUpiSupported();
    }

    public LiveData<Boolean> isCardPaymentSupported() {
        return paymentRepository.getIsNetBankingSupported();
    }

    public LiveData<Boolean> isNetBankingPaymentSupported() {
        return paymentRepository.getIsCardSupported();
    }

    public LiveData<Boolean> isSBINetBankingSupported() {
        return paymentRepository.getIsNetBankingSBINSupported();
    }

    public LiveData<Boolean> isHDFCNetBankingSupported() {
        return paymentRepository.getIsNetBankingHDFCSupported();
    }

    public LiveData<Boolean> isBBNetBankingSupported() {
        return paymentRepository.getIsNetBankingBBSupported();
    }

    public LiveData<Boolean> isCanaraNetBankingSupported() {
        return paymentRepository.getIsNetBankingCANARASupported();
    }

    public LiveData<Boolean> isICICINetBankingSupported() {
        return paymentRepository.getIsNetBankingICICSupported();
    }

    public LiveData<Boolean> isKotakNetBankingSupported() {
        return paymentRepository.getIsNetBankingKotakSupported();
    }

    public LiveData<List<NetBanking>> getNetBankingList() {
        return paymentRepository.getNetBankList();
    }

    public LiveData<Boolean> onVpaValidationSuccess() {
        return paymentRepository.getIsValidVpa();
    }

    public LiveData<String> onVpaValidationFailed() {
        return paymentRepository.getErrorValidateVPA();
    }
    public LiveData<Boolean> onSwitchPaymentViewWebCheckout() {
        return paymentRepository.getOnSwitchToWebCheckout();
    }
    public LiveData<Boolean> onSwitchPaymentViewDefault() {
        return paymentRepository.getOnSwitchDefaultView();
    }
    public LiveData<String> getPaymentMethodError() {
        return paymentRepository.getPaymentMethodLoadError();
    }
    public LiveData<PaymentBaseShareData.PaymentSuccess> getPaymentSuccess() {
        return paymentRepository.getPaymentSuccessMutableLiveData();
    }
    public LiveData<PaymentBaseShareData.PaymentError> getPaymentError() {
        return paymentRepository.getPaymentErrorMutableLiveData();
    }
    public void validateVPA(String vpa) {
        if (basicInputValidation(vpa, vpa.length())) {
            paymentRepository.validateVPA(vpa);
        }
    }

    public void validateCard(String cardNumber, String name ,String month, String year, String cvv) {
        if (basicInputValidation(cardNumber, 16)) {
            if(basicInputValidation(cardNumber, name.length())) {
                if (basicInputValidation(month, 2)) {
                    if (basicInputValidation(year, 2)) {
                        if (basicInputValidation(cvv, 3)) {
                            cardPaymentValidationSuccess.setValue(true);
                        } else {
                            cardPaymentValidationError.setValue("Not a valid cvv");
                        }
                    } else {
                        cardPaymentValidationError.setValue("Not a valid year");
                    }
                } else {
                    cardPaymentValidationError.setValue("Not a valid month");
                }
            }
            else {
                cardPaymentValidationError.setValue("Enter valid name");
            }
        } else {
            cardPaymentValidationError.setValue("Enter valid cardNumber");
        }
    }
    public void processPayment(JSONObject payload) {
      paymentRepository.processPayment(payload);
    }

    public MutableLiveData<String> getCardPaymentValidationError() {
        return cardPaymentValidationError;
    }

    public MutableLiveData<Boolean> getCardPaymentValidationSuccess() {
        return cardPaymentValidationSuccess;
    }
}
