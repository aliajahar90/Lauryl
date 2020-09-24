
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrdersResponse {

    @SerializedName("data")
    @Expose
    private MyOrdersData data;

    public MyOrdersData getData() {
        return data;
    }

    public void setData(MyOrdersData data) {
        this.data = data;
    }

}
