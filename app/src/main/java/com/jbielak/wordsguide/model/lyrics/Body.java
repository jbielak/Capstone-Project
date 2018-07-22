package com.jbielak.wordsguide.model.lyrics;


import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Body implements Parcelable {

    @SerializedName("lyrics")
    @Expose
    private Lyrics lyrics;
    public final static Parcelable.Creator<Body> CREATOR = new Creator<Body>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Body createFromParcel(Parcel in) {
            return new Body(in);
        }

        public Body[] newArray(int size) {
            return (new Body[size]);
        }

    };

    protected Body(Parcel in) {
        this.lyrics = ((Lyrics) in.readValue((Lyrics.class.getClassLoader())));
    }

    public Body() {
    }

    public Lyrics getLyrics() {
        return lyrics;
    }

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("lyrics", lyrics).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lyrics);
    }

    public int describeContents() {
        return 0;
    }

}