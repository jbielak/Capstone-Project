package com.jbielak.wordsguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackSearchResponse implements Parcelable {

    @SerializedName("message")
    @Expose
    private Message message;
    public final static Parcelable.Creator<TrackSearchResponse> CREATOR = new Creator<TrackSearchResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TrackSearchResponse createFromParcel(Parcel in) {
            return new TrackSearchResponse(in);
        }

        public TrackSearchResponse[] newArray(int size) {
            return (new TrackSearchResponse[size]);
        }

    };

    protected TrackSearchResponse(Parcel in) {
        this.message = ((Message) in.readValue((Message.class.getClassLoader())));
    }

    public TrackSearchResponse() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }
}
