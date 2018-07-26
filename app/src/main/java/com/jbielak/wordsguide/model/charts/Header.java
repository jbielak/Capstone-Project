package com.jbielak.wordsguide.model.charts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Header implements Parcelable {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("execute_time")
    @Expose
    private Double executeTime;
    public final static Parcelable.Creator<Header> CREATOR = new Creator<Header>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Header createFromParcel(Parcel in) {
            return new Header(in);
        }

        public Header[] newArray(int size) {
            return (new Header[size]);
        }

    };

    protected Header(Parcel in) {
        this.statusCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.executeTime = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Header() {
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Double getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Double executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusCode", statusCode)
                .append("executeTime", executeTime).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(statusCode);
        dest.writeValue(executeTime);
    }

    public int describeContents() {
        return 0;
    }

}