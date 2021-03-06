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
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.orders.create.CreateOrderViewModel;
import versatile.project.lauryl.payment.data.NetBanking;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.data.repsitory.PaymentRepository;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentViewModel extends CreateOrderViewModel {
    private Razorpay razorpay;
    PaymentRepository paymentRepository;
    SingleLiveEvent<String> cardPaymentValidationError=new SingleLiveEvent<>();
    SingleLiveEvent<Boolean> cardPaymentValidationSuccess=new SingleLiveEvent<>();

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
        return paymentRepository.getPaymentSuccessSingleLiveEvent();
    }
    public LiveData<PaymentBaseShareData.PaymentError> getPaymentError() {
        return paymentRepository.getPaymentErrorSingleLiveEvent();
    }
    public void validateVPA(String vpa) {
        if (basicInputValidation(vpa, vpa.length())) {
            paymentRepository.validateVPA(vpa);
        }
    }

    public void validateCard(String cardNumber, String name, String month, String year, String cvv) {
        if (basicInputValidation(cardNumber, cardNumber.length())) {
            if (basicInputValidation(name, name.length())) {
                if (basicInputValidation(month, 2)) {
                    if (basicInputValidation(year, 2)) {
                        if (basicInputValidation(cvv, 3)) {
                            cardPaymentValidationSuccess.setValue(true);
                        } else {
                            cardPaymentValidationError.setValue("Enter a valid cvv");
                        }
                    } else {
                        cardPaymentValidationError.setValue("Enter last two digit of year");
                    }
                } else {
                    cardPaymentValidationError.setValue("Enter a valid month");
                }
            } else {
                cardPaymentValidationError.setValue("Enter a valid name");
            }
        } else {
            cardPaymentValidationError.setValue("Enter a valid cardNumber");
        }
    }
    public void processPayment(JSONObject payload) {
      paymentRepository.processPayment(payload);
    }

    public SingleLiveEvent<String> getCardPaymentValidationError() {
        return cardPaymentValidationError;
    }

    public SingleLiveEvent<Boolean> getCardPaymentValidationSuccess() {
        return cardPaymentValidationSuccess;
    }
}
