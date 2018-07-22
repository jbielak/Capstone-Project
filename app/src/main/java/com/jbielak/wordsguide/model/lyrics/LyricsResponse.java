package com.jbielak.wordsguide.model.lyrics;


import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class LyricsResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private Message message;
    public final static Parcelable.Creator<LyricsResponse> CREATOR = new Creator<LyricsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LyricsResponse createFromParcel(Parcel in) {
            return new LyricsResponse(in);
        }

        public LyricsResponse[] newArray(int size) {
            return (new LyricsResponse[size]);
        }

    };

    protected LyricsResponse(Parcel in) {
        this.message = ((Message) in.readValue((Message.class.getClassLoader())));
    }

    public LyricsResponse() {
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