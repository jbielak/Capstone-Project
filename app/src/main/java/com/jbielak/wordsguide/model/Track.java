package com.jbielak.wordsguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Track implements Parcelable {

    @SerializedName("track_id")
    @Expose
    private Integer trackId;
    @SerializedName("track_mbid")
    @Expose
    private String trackMbid;
    @SerializedName("track_name")
    @Expose
    private String trackName;
    @SerializedName("track_rating")
    @Expose
    private Integer trackRating;
    @SerializedName("track_length")
    @Expose
    private Integer trackLength;
    @SerializedName("commontrack_id")
    @Expose
    private Integer commontrackId;
    @SerializedName("instrumental")
    @Expose
    private Integer instrumental;
    @SerializedName("explicit")
    @Expose
    private Integer explicit;
    @SerializedName("has_lyrics")
    @Expose
    private Integer hasLyrics;
    @SerializedName("has_lyrics_crowd")
    @Expose
    private Integer hasLyricsCrowd;
    @SerializedName("has_subtitles")
    @Expose
    private Integer hasSubtitles;
    @SerializedName("has_richsync")
    @Expose
    private Integer hasRichsync;
    @SerializedName("num_favourite")
    @Expose
    private Integer numFavourite;
    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;
    @SerializedName("subtitle_id")
    @Expose
    private Integer subtitleId;
    @SerializedName("album_id")
    @Expose
    private Integer albumId;
    @SerializedName("album_name")
    @Expose
    private String albumName;
    @SerializedName("artist_id")
    @Expose
    private Integer artistId;
    @SerializedName("artist_mbid")
    @Expose
    private String artistMbid;
    @SerializedName("artist_name")
    @Expose
    private String artistName;
    @SerializedName("album_coverart_100x100")
    @Expose
    private String albumCoverart100x100;
    @SerializedName("album_coverart_350x350")
    @Expose
    private String albumCoverart350x350;
    @SerializedName("album_coverart_500x500")
    @Expose
    private String albumCoverart500x500;
    @SerializedName("album_coverart_800x800")
    @Expose
    private String albumCoverart800x800;
    @SerializedName("track_share_url")
    @Expose
    private String trackShareUrl;
    @SerializedName("track_edit_url")
    @Expose
    private String trackEditUrl;
    @SerializedName("commontrack_vanity_id")
    @Expose
    private String commontrackVanityId;
    @SerializedName("restricted")
    @Expose
    private Integer restricted;
    @SerializedName("first_release_date")
    @Expose
    private String firstReleaseDate;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;

    public final static Parcelable.Creator<Track> CREATOR = new Creator<Track>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        public Track[] newArray(int size) {
            return (new Track[size]);
        }

    };

    protected Track(Parcel in) {
        this.trackId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.trackMbid = ((String) in.readValue((String.class.getClassLoader())));
        this.trackName = ((String) in.readValue((String.class.getClassLoader())));
        this.trackRating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.trackLength = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.commontrackId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.instrumental = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.explicit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hasLyrics = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hasLyricsCrowd = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hasSubtitles = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hasRichsync = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.numFavourite = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.lyricsId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.subtitleId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.albumId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.albumName = ((String) in.readValue((String.class.getClassLoader())));
        this.artistId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.artistMbid = ((String) in.readValue((String.class.getClassLoader())));
        this.artistName = ((String) in.readValue((String.class.getClassLoader())));
        this.albumCoverart100x100 = ((String) in.readValue((String.class.getClassLoader())));
        this.albumCoverart350x350 = ((String) in.readValue((String.class.getClassLoader())));
        this.albumCoverart500x500 = ((String) in.readValue((String.class.getClassLoader())));
        this.albumCoverart800x800 = ((String) in.readValue((String.class.getClassLoader())));
        this.trackShareUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.trackEditUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.commontrackVanityId = ((String) in.readValue((String.class.getClassLoader())));
        this.restricted = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.firstReleaseDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Track() {
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getTrackMbid() {
        return trackMbid;
    }

    public void setTrackMbid(String trackMbid) {
        this.trackMbid = trackMbid;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackRating() {
        return trackRating;
    }

    public void setTrackRating(Integer trackRating) {
        this.trackRating = trackRating;
    }

    public Integer getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(Integer trackLength) {
        this.trackLength = trackLength;
    }

    public Integer getCommontrackId() {
        return commontrackId;
    }

    public void setCommontrackId(Integer commontrackId) {
        this.commontrackId = commontrackId;
    }

    public Integer getInstrumental() {
        return instrumental;
    }

    public void setInstrumental(Integer instrumental) {
        this.instrumental = instrumental;
    }

    public Integer getExplicit() {
        return explicit;
    }

    public void setExplicit(Integer explicit) {
        this.explicit = explicit;
    }

    public Integer getHasLyrics() {
        return hasLyrics;
    }

    public void setHasLyrics(Integer hasLyrics) {
        this.hasLyrics = hasLyrics;
    }

    public Integer getHasLyricsCrowd() {
        return hasLyricsCrowd;
    }

    public void setHasLyricsCrowd(Integer hasLyricsCrowd) {
        this.hasLyricsCrowd = hasLyricsCrowd;
    }

    public Integer getHasSubtitles() {
        return hasSubtitles;
    }

    public void setHasSubtitles(Integer hasSubtitles) {
        this.hasSubtitles = hasSubtitles;
    }

    public Integer getHasRichsync() {
        return hasRichsync;
    }

    public void setHasRichsync(Integer hasRichsync) {
        this.hasRichsync = hasRichsync;
    }

    public Integer getNumFavourite() {
        return numFavourite;
    }

    public void setNumFavourite(Integer numFavourite) {
        this.numFavourite = numFavourite;
    }

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public Integer getSubtitleId() {
        return subtitleId;
    }

    public void setSubtitleId(Integer subtitleId) {
        this.subtitleId = subtitleId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getArtistMbid() {
        return artistMbid;
    }

    public void setArtistMbid(String artistMbid) {
        this.artistMbid = artistMbid;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumCoverart100x100() {
        return albumCoverart100x100;
    }

    public void setAlbumCoverart100x100(String albumCoverart100x100) {
        this.albumCoverart100x100 = albumCoverart100x100;
    }

    public String getAlbumCoverart350x350() {
        return albumCoverart350x350;
    }

    public void setAlbumCoverart350x350(String albumCoverart350x350) {
        this.albumCoverart350x350 = albumCoverart350x350;
    }

    public String getAlbumCoverart500x500() {
        return albumCoverart500x500;
    }

    public void setAlbumCoverart500x500(String albumCoverart500x500) {
        this.albumCoverart500x500 = albumCoverart500x500;
    }

    public String getAlbumCoverart800x800() {
        return albumCoverart800x800;
    }

    public void setAlbumCoverart800x800(String albumCoverart800x800) {
        this.albumCoverart800x800 = albumCoverart800x800;
    }

    public String getTrackShareUrl() {
        return trackShareUrl;
    }

    public void setTrackShareUrl(String trackShareUrl) {
        this.trackShareUrl = trackShareUrl;
    }

    public String getTrackEditUrl() {
        return trackEditUrl;
    }

    public void setTrackEditUrl(String trackEditUrl) {
        this.trackEditUrl = trackEditUrl;
    }

    public String getCommontrackVanityId() {
        return commontrackVanityId;
    }

    public void setCommontrackVanityId(String commontrackVanityId) {
        this.commontrackVanityId = commontrackVanityId;
    }

    public Integer getRestricted() {
        return restricted;
    }

    public void setRestricted(Integer restricted) {
        this.restricted = restricted;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(trackId);
        dest.writeValue(trackMbid);
        dest.writeValue(trackName);
        dest.writeValue(trackRating);
        dest.writeValue(trackLength);
        dest.writeValue(commontrackId);
        dest.writeValue(instrumental);
        dest.writeValue(explicit);
        dest.writeValue(hasLyrics);
        dest.writeValue(hasLyricsCrowd);
        dest.writeValue(hasSubtitles);
        dest.writeValue(hasRichsync);
        dest.writeValue(numFavourite);
        dest.writeValue(lyricsId);
        dest.writeValue(subtitleId);
        dest.writeValue(albumId);
        dest.writeValue(albumName);
        dest.writeValue(artistId);
        dest.writeValue(artistMbid);
        dest.writeValue(artistName);
        dest.writeValue(albumCoverart100x100);
        dest.writeValue(albumCoverart350x350);
        dest.writeValue(albumCoverart500x500);
        dest.writeValue(albumCoverart800x800);
        dest.writeValue(trackShareUrl);
        dest.writeValue(trackEditUrl);
        dest.writeValue(commontrackVanityId);
        dest.writeValue(restricted);
        dest.writeValue(firstReleaseDate);
        dest.writeValue(updatedTime);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("trackId", trackId)
                .append("trackMbid", trackMbid)
                .append("trackName", trackName)
                .append("trackRating", trackRating)
                .append("trackLength", trackLength)
                .append("commontrackId", commontrackId)
                .append("instrumental", instrumental)
                .append("explicit", explicit)
                .append("hasLyrics", hasLyrics)
                .append("hasLyricsCrowd", hasLyricsCrowd)
                .append("hasSubtitles", hasSubtitles)
                .append("hasRichsync", hasRichsync)
                .append("numFavourite", numFavourite)
                .append("lyricsId", lyricsId)
                .append("subtitleId", subtitleId)
                .append("albumId", albumId)
                .append("albumName", albumName)
                .append("artistId", artistId)
                .append("artistMbid", artistMbid)
                .append("artistName", artistName)
                .append("albumCoverart100x100", albumCoverart100x100)
                .append("albumCoverart350x350", albumCoverart350x350)
                .append("albumCoverart500x500", albumCoverart500x500)
                .append("albumCoverart800x800", albumCoverart800x800)
                .append("trackShareUrl", trackShareUrl)
                .append("trackEditUrl", trackEditUrl)
                .append("commontrackVanityId", commontrackVanityId)
                .append("restricted", restricted)
                .append("firstReleaseDate", firstReleaseDate)
                .append("updatedTime", updatedTime)
                .toString();
    }
}
