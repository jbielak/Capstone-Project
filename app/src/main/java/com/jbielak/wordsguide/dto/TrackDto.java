package com.jbielak.wordsguide.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class TrackDto implements Parcelable {

    private Integer trackId;
    private String trackName;
    private String artistName;
    private String albumName;
    private String firstReleaseDate;
    private String albumCoverart100x100;
    private String trackShareUrl;

    public TrackDto() {
    }

    public TrackDto(Integer trackId, String trackName, String artistName, String albumName,
                    String firstReleaseDate, String albumCoverart100x100, String trackShareUrl) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.firstReleaseDate = firstReleaseDate;
        this.albumCoverart100x100 = albumCoverart100x100;
        this.trackShareUrl = trackShareUrl;
    }

    protected TrackDto(Parcel in) {
        this.trackId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.trackName = in.readString();
        this.artistName = in.readString();
        this.albumName = in.readString();
        this.firstReleaseDate = in.readString();
        this.albumCoverart100x100 = in.readString();
        this.trackShareUrl = in.readString();
    }

    public static final Creator<TrackDto> CREATOR = new Creator<TrackDto>() {
        @Override
        public TrackDto createFromParcel(Parcel in) {
            return new TrackDto(in);
        }

        @Override
        public TrackDto[] newArray(int size) {
            return new TrackDto[size];
        }
    };

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public String getAlbumCoverart100x100() {
        return albumCoverart100x100;
    }

    public void setAlbumCoverart100x100(String albumCoverart100x100) {
        this.albumCoverart100x100 = albumCoverart100x100;
    }

    public String getTrackShareUrl() {
        return trackShareUrl;
    }

    public void setTrackShareUrl(String trackShareUrl) {
        this.trackShareUrl = trackShareUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(trackId);
        parcel.writeString(trackName);
        parcel.writeString(artistName);
        parcel.writeString(albumName);
        parcel.writeString(firstReleaseDate);
        parcel.writeString(albumCoverart100x100);
        parcel.writeString(trackShareUrl);
    }
}
