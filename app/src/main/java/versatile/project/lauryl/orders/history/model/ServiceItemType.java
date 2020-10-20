package versatile.project.lauryl.orders.history.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceItemType implements Parcelable {
    private String scannedItemType;

    public void setScannedItemType(String scannedItemType) {
        this.scannedItemType = scannedItemType;
    }

    public void setQtyPurchased(String qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    private String qtyPurchased;
    private String productPrice;

    protected ServiceItemType(Parcel in) {
        scannedItemType = in.readString();
        qtyPurchased = in.readString();
        productPrice = in.readString();
    }

    public String getScannedItemType() {
        return scannedItemType;
    }

    public String getQtyPurchased() {
        return qtyPurchased;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public ServiceItemType(String scannedItemType, String qtyPurchased, String productPrice) {
        this.scannedItemType = scannedItemType;
        this.qtyPurchased = qtyPurchased;
        this.productPrice = productPrice;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(scannedItemType);
        dest.writeString(qtyPurchased);
        dest.writeString(productPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServiceItemType> CREATOR = new Creator<ServiceItemType>() {
        @Override
        public ServiceItemType createFromParcel(Parcel in) {
            return new ServiceItemType(in);
        }

        @Override
        public ServiceItemType[] newArray(int size) {
            return new ServiceItemType[size];
        }
    };
}
