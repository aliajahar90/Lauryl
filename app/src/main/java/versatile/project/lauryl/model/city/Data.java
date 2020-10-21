package versatile.project.lauryl.model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("list")
    @Expose
    private java.util.List<CityModel> list = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public java.util.List<CityModel> getList() {
        return list;
    }

    public void setList(java.util.List<CityModel> list) {
        this.list = list;
    }

}
