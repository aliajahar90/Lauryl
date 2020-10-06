package versatile.project.lauryl.payment.util;

import androidx.fragment.app.Fragment;

import versatile.project.lauryl.base.DeferredFragmentTransaction;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;

public abstract class PaymentDefferedFragmentTransaction extends DeferredFragmentTransaction {
    private PaymentBaseShareData.PaymentError paymentError;

    public PaymentBaseShareData.PaymentError getPaymentError() {
        return paymentError;
    }

    public void setPaymentError(PaymentBaseShareData.PaymentError paymentError) {
        this.paymentError = paymentError;
    }
}
