package versatile.project.lauryl.pickup.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateTimeMap {
    String date;
    String time;
    private String noOfSlots;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoOfSlots() {
        return noOfSlots;
    }

    public void setNoOfSlots(String noOfSlots) {
        this.noOfSlots = noOfSlots;
    }
}
