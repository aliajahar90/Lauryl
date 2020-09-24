
package versatile.project.lauryl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrdersData {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("list")
    @Expose
    private List<MyOrdersDataItem> list = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<MyOrdersDataItem> getList() {
        return list;
    }

    public void setList(List<MyOrdersDataItem> list) {
        this.list = list;
    }

}
