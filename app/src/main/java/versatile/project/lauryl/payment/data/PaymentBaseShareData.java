package versatile.project.lauryl.payment.data;

import java.io.Serializable;

public class PaymentBaseShareData implements Serializable {
    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
