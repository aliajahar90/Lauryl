package versatile.project.lauryl.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressData {
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("list")
    @Expose
    private java.util.List<AddressModel> list = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public java.util.List<AddressModel> getList() {
        return list;
    }

    public void setList(java.util.List<AddressModel> list) {
        this.list = list;
    }

}
