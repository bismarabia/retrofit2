package systems.hotech.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyModel {
    @SerializedName("success")
    private boolean success;
    @SerializedName("msg")
    private String msg;
    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private List data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean aSuccess) {
        success = aSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String aMsg) {
        msg = aMsg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int aCount) {
        count = aCount;
    }

    public List getData() {
        return data;
    }

    public void setData(List aData) {
        data = aData;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}