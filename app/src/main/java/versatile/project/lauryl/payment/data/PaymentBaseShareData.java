package versatile.project.lauryl.payment.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.razorpay.PaymentData;

import java.io.Serializable;

import kotlin.Metadata;
import versatile.project.lauryl.orders.create.model.CreateOrderData;

public class PaymentBaseShareData implements Serializable {
    private int viewType;
    private PaymentSuccess paymentSuccess;
    private PaymentError paymentError;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public static class PaymentSuccess implements Serializable{
        String paymentTransactionId;
        PaymentData paymentData;
        CreateOrderData createOrderData;
        String razorOrderId;

        public String getPaymenMethod() {
            return paymenMethod;
        }

        public void setPaymenMethod(String paymenMethod) {
            this.paymenMethod = paymenMethod;
        }

        String paymenMethod;

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

        public CreateOrderData getCreateOrderData() {
            return createOrderData;
        }

        public void setCreateOrderData(CreateOrderData createOrderData) {
            this.createOrderData = createOrderData;
        }

        public String getRazorOrderId() {
            return razorOrderId;
        }

        public void setRazorOrderId(String razorOrderId) {
            this.razorOrderId = razorOrderId;
        }
    }
    public static class PaymentError implements Serializable {
        int code;
        String description;
        PaymentData paymentData;
        private String serializedOrderInformation;
        private String serializedServiceInformation;
        private String serializedProfileInformation;
        private String serializedAddressInformation;

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

        public String getSerializedOrderInformation() {
            return serializedOrderInformation;
        }

        public void setSerializedOrderInformation(String serializedOrderInformation) {
            this.serializedOrderInformation = serializedOrderInformation;
        }

        public String getSerializedServiceInformation() {
            return serializedServiceInformation;
        }

        public void setSerializedServiceInformation(String serializedServiceInformation) {
            this.serializedServiceInformation = serializedServiceInformation;
        }

        public String getSerializedProfileInformation() {
            return serializedProfileInformation;
        }

        public void setSerializedProfileInformation(String serializedProfileInformation) {
            this.serializedProfileInformation = serializedProfileInformation;
        }

        public String getSerializedAddressInformation() {
            return serializedAddressInformation;
        }

        public void setSerializedAddressInformation(String serializedAddressInformation) {
            this.serializedAddressInformation = serializedAddressInformation;
        }

    }

    public PaymentSuccess getPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(PaymentSuccess paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }

    public PaymentError getPaymentError() {
        return paymentError;
    }

    public void setPaymentError(PaymentError paymentError) {
        this.paymentError = paymentError;
    }

    public class RazorPayError implements Serializable{
        @SerializedName("error")
        @Expose
        private ErrorDescription error;
        @SerializedName("http_status_code")
        @Expose
        private Integer httpStatusCode;

        public ErrorDescription getError() {
            return error;
        }

        public void setError(ErrorDescription error) {
            this.error = error;
        }

        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }
    }
    public class ErrorDescription implements Serializable {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("step")
        @Expose
        private String step;
        @SerializedName("reason")
        @Expose
        private String reason;
        @SerializedName("field")
        @Expose
        private String field;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

    }

}
