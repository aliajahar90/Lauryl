package versatile.project.lauryl.payment.data;

import com.razorpay.PaymentData;

import java.io.Serializable;

public class PaymentBaseShareData implements Serializable {
    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public static class PaymentSuccess implements Serializable{
        String paymentTransactionId;
        PaymentData paymentData;

        public String getPaymentTransactionId() {
            return paymentTransactionId;
        }

        public PaymentData getPaymentData() {
            return paymentData;
        }

        public void setPaymentTransactionId(String paymentTransactionId) {
            this.paymentTransactionId = paymentTransactionId;
        }

        public void setPaymentData(PaymentData paymentData) {
            this.paymentData = paymentData;
        }
    }
    public static class PaymentError implements Serializable {
        int code;
        String description;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public PaymentData getPaymentData() {
            return paymentData;
        }

        public void setPaymentData(PaymentData paymentData) {
            this.paymentData = paymentData;
        }

        PaymentData paymentData;

    }
}
