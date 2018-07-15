package com.jbielak.wordsguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Message implements Parcelable {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("body")
    @Expose
    private Body body;
    public final static Parcelable.Creator<Message> CREATOR = new Creator<Message>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return (new Message[size]);
        }

    };

    protected Message(Parcel in) {
        this.header = ((Header) in.readValue((Header.class.getClassLoader())));
        this.body = ((Body) in.readValue((Body.class.getClassLoader())));
    }

    public Message() {
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(header);
        dest.writeValue(body);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("header", header)
                .append("body", body)
                .toString();
    }
}
