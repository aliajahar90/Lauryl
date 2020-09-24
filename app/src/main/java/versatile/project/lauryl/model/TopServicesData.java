
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopServicesData {

    @SerializedName("totalCount")
    @Expose
    public Integer totalCount;
    @SerializedName("list")
    @Expose
    public List<TopServicesDataItem> list = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public java.util.List<TopServicesDataItem> getList() {
        return list;
    }

    public void setList(List<TopServicesDataItem> list) {
        this.list = list;
    }

}
