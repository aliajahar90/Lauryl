package versatile.project.lauryl.payment.data;

public class UpiPaymentRequest extends BasePaymentRequestData {
    private String vpa;
    public UpiPaymentRequest(String amount, String contact, String email, String method) {
        super(amount, contact, email, method);
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }
}
