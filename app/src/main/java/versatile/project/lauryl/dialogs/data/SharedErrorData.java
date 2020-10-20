package versatile.project.lauryl.dialogs.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SharedErrorData  {

    @SerializedName("nameValuePairs")
    @Expose
    private ErrorData nameValuePairs;

    public ErrorData getNameValuePairs() {
        return nameValuePairs;
    }

    public void setNameValuePairs(ErrorData nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }
    public static class ErrorData {
        private int logoId;
        private String tile;

        public int getLogoId() {
            return logoId;
        }

        public void setLogoId(int logoId) {
            this.logoId = logoId;
        }

        public String getTile() {
            return tile;
        }

        public void setTile(String tile) {
            this.tile = tile;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCancelTxt() {
            return cancelTxt;
        }

        public void setCancelTxt(String cancelTxt) {
            this.cancelTxt = cancelTxt;
        }

        public String getProceedTxt() {
            return proceedTxt;
        }

        public void setProceedTxt(String proceedTxt) {
            this.proceedTxt = proceedTxt;
        }

        private String msg;
        private String cancelTxt;
        private String proceedTxt;
    }

}
