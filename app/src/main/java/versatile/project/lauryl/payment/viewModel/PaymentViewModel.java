package versatile.project.lauryl.payment.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.razorpay.Razorpay;

import org.json.JSONObject;

import java.util.List;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.payment.data.NetBanking;
import versatile.project.lauryl.payment.data.repsitory.PaymentRepository;

public class PaymentViewModel extends BaseViewModel {
    private Razorpay razorpay;
    PaymentRepository paymentRepository;

    public PaymentViewModel(Razorpay razorpay) {
        this.razorpay = razorpay;
        this.paymentRepository = PaymentRepository.getInstance(razorpay);
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
    public LiveData<Integer> onSwitchPaymentView() {
        return paymentRepository.getOnSwitchView();
    }


    public void validateVPA(String vpa) {
        if (basicInputValidation(vpa, vpa.length())) {
            paymentRepository.validateVPA(vpa);
        }
    }
    public void processPayment(JSONObject payload) {
      paymentRepository.processPayment(payload);
    }
}
