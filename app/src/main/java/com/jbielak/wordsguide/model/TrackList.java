package com.jbielak.wordsguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TrackList implements Parcelable {

    @SerializedName("track")
    @Expose
    private Track track;
    public final static Parcelable.Creator<TrackList> CREATOR = new Creator<TrackList>() {

        @SuppressWarnings({
                "unchecked"
        })
        public TrackList createFromParcel(Parcel in) {
            return new TrackList(in);
        }

        public TrackList[] newArray(int size) {
            return (new TrackList[size]);
        }

    };

    protected TrackList(Parcel in) {
        this.track = ((Track) in.readValue((Track.class.getClassLoader())));
    }

    public TrackList() {
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(track);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("track", track)
                .toString();
    }

}
