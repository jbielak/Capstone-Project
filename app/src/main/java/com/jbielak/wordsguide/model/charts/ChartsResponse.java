package com.jbielak.wordsguide.model.charts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChartsResponse implements Parcelable {

    @SerializedName("message")
    @Expose
    private Message message;
    public final static Parcelable.Creator<ChartsResponse> CREATOR = new Creator<ChartsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChartsResponse createFromParcel(Parcel in) {
            return new ChartsResponse(in);
        }

        public ChartsResponse[] newArray(int size) {
            return (new ChartsResponse[size]);
        }

    };

    protected ChartsResponse(Parcel in) {
        this.message = ((Message) in.readValue((Message.class.getClassLoader())));
    }

    public ChartsResponse() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("message", message).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}