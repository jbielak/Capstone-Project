package com.jbielak.wordsguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {

    @SerializedName("track_list")
    @Expose
    private List<TrackList> trackList = null;
    public final static Parcelable.Creator<Body> CREATOR = new Parcelable.Creator<Body>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Body createFromParcel(Parcel in) {
            return new Body(in);
        }

        public Body[] newArray(int size) {
            return (new Body[size]);
        }

    }
            ;

    protected Body(Parcel in) {
        in.readList(this.trackList, (TrackList.class.getClassLoader()));
    }

    public Body() {
    }

    public List<TrackList> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<TrackList> trackList) {
        this.trackList = trackList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(trackList);
    }

    public int describeContents() {
        return 0;
    }
}
