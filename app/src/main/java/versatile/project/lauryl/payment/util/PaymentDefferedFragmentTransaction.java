package versatile.project.lauryl.payment.util;

import androidx.fragment.app.Fragment;

import versatile.project.lauryl.base.DeferredFragmentTransaction;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;

public abstract class PaymentDefferedFragmentTransaction extends DeferredFragmentTransaction {
    private PaymentBaseShareData paymentError;

    public PaymentBaseShareData getPaymentError() {
        return paymentError;
    }

    public void setPaymentError(PaymentBaseShareData paymentError) {
        this.paymentError = paymentError;
    }
}
