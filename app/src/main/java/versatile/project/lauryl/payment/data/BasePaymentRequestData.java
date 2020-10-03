package versatile.project.lauryl.payment.data;

import java.io.Serializable;

class BasePaymentRequestData implements Serializable {
    private String amount;
    private String contact;
    private String email;
    private String method;

    public BasePaymentRequestData(String amount, String contact, String email, String method) {
        this.amount = amount;
        this.contact = contact;
        this.email = email;
        this.method = method;
    }
}
