
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopServicesResponse {

    @SerializedName("data")
    @Expose
    public TopServicesData data;

    public TopServicesData getData() {
        return data;
    }

    public void setData(TopServicesData data) {
        this.data = data;
    }

}
