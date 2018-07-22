package com.jbielak.wordsguide.model.lyrics;


import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Lyrics implements Parcelable {

    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;
    @SerializedName("can_edit")
    @Expose
    private Integer canEdit;
    @SerializedName("locked")
    @Expose
    private Integer locked;
    @SerializedName("published_status")
    @Expose
    private Integer publishedStatus;
    @SerializedName("action_requested")
    @Expose
    private String actionRequested;
    @SerializedName("verified")
    @Expose
    private Integer verified;
    @SerializedName("restricted")
    @Expose
    private Integer restricted;
    @SerializedName("instrumental")
    @Expose
    private Integer instrumental;
    @SerializedName("explicit")
    @Expose
    private Integer explicit;
    @SerializedName("lyrics_body")
    @Expose
    private String lyricsBody;
    @SerializedName("lyrics_language")
    @Expose
    private String lyricsLanguage;
    @SerializedName("lyrics_language_description")
    @Expose
    private String lyricsLanguageDescription;
    @SerializedName("script_tracking_url")
    @Expose
    private String scriptTrackingUrl;
    @SerializedName("pixel_tracking_url")
    @Expose
    private String pixelTrackingUrl;
    @SerializedName("html_tracking_url")
    @Expose
    private String htmlTrackingUrl;
    @SerializedName("lyrics_copyright")
    @Expose
    private String lyricsCopyright;
    @SerializedName("writer_list")
    @Expose
    private List<Object> writerList = null;
    @SerializedName("publisher_list")
    @Expose
    private List<Object> publisherList = null;
    @SerializedName("backlink_url")
    @Expose
    private String backlinkUrl;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    public final static Parcelable.Creator<Lyrics> CREATOR = new Creator<Lyrics>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Lyrics createFromParcel(Parcel in) {
            return new Lyrics(in);
        }

        public Lyrics[] newArray(int size) {
            return (new Lyrics[size]);
        }

    };

    protected Lyrics(Parcel in) {
        this.lyricsId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.canEdit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.locked = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.publishedStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.actionRequested = ((String) in.readValue((String.class.getClassLoader())));
        this.verified = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.restricted = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.instrumental = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.explicit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.lyricsBody = ((String) in.readValue((String.class.getClassLoader())));
        this.lyricsLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.lyricsLanguageDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.scriptTrackingUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.pixelTrackingUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.htmlTrackingUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.lyricsCopyright = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.writerList, (java.lang.Object.class.getClassLoader()));
        in.readList(this.publisherList, (java.lang.Object.class.getClassLoader()));
        this.backlinkUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Lyrics() {
    }

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public Integer getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Integer canEdit) {
        this.canEdit = canEdit;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getPublishedStatus() {
        return publishedStatus;
    }

    public void setPublishedStatus(Integer publishedStatus) {
        this.publishedStatus = publishedStatus;
    }

    public String getActionRequested() {
        return actionRequested;
    }

    public void setActionRequested(String actionRequested) {
        this.actionRequested = actionRequested;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Integer getRestricted() {
        return restricted;
    }

    public void setRestricted(Integer restricted) {
        this.restricted = restricted;
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

    public String getLyricsBody() {
        return lyricsBody;
    }

    public void setLyricsBody(String lyricsBody) {
        this.lyricsBody = lyricsBody;
    }

    public String getLyricsLanguage() {
        return lyricsLanguage;
    }

    public void setLyricsLanguage(String lyricsLanguage) {
        this.lyricsLanguage = lyricsLanguage;
    }

    public String getLyricsLanguageDescription() {
        return lyricsLanguageDescription;
    }

    public void setLyricsLanguageDescription(String lyricsLanguageDescription) {
        this.lyricsLanguageDescription = lyricsLanguageDescription;
    }

    public String getScriptTrackingUrl() {
        return scriptTrackingUrl;
    }

    public void setScriptTrackingUrl(String scriptTrackingUrl) {
        this.scriptTrackingUrl = scriptTrackingUrl;
    }

    public String getPixelTrackingUrl() {
        return pixelTrackingUrl;
    }

    public void setPixelTrackingUrl(String pixelTrackingUrl) {
        this.pixelTrackingUrl = pixelTrackingUrl;
    }

    public String getHtmlTrackingUrl() {
        return htmlTrackingUrl;
    }

    public void setHtmlTrackingUrl(String htmlTrackingUrl) {
        this.htmlTrackingUrl = htmlTrackingUrl;
    }

    public String getLyricsCopyright() {
        return lyricsCopyright;
    }

    public void setLyricsCopyright(String lyricsCopyright) {
        this.lyricsCopyright = lyricsCopyright;
    }

    public List<Object> getWriterList() {
        return writerList;
    }

    public void setWriterList(List<Object> writerList) {
        this.writerList = writerList;
    }

    public List<Object> getPublisherList() {
        return publisherList;
    }

    public void setPublisherList(List<Object> publisherList) {
        this.publisherList = publisherList;
    }

    public String getBacklinkUrl() {
        return backlinkUrl;
    }

    public void setBacklinkUrl(String backlinkUrl) {
        this.backlinkUrl = backlinkUrl;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("lyricsId", lyricsId).append("canEdit", canEdit).append("locked", locked).append("publishedStatus", publishedStatus).append("actionRequested", actionRequested).append("verified", verified).append("restricted", restricted).append("instrumental", instrumental).append("explicit", explicit).append("lyricsBody", lyricsBody).append("lyricsLanguage", lyricsLanguage).append("lyricsLanguageDescription", lyricsLanguageDescription).append("scriptTrackingUrl", scriptTrackingUrl).append("pixelTrackingUrl", pixelTrackingUrl).append("htmlTrackingUrl", htmlTrackingUrl).append("lyricsCopyright", lyricsCopyright).append("writerList", writerList).append("publisherList", publisherList).append("backlinkUrl", backlinkUrl).append("updatedTime", updatedTime).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lyricsId);
        dest.writeValue(canEdit);
        dest.writeValue(locked);
        dest.writeValue(publishedStatus);
        dest.writeValue(actionRequested);
        dest.writeValue(verified);
        dest.writeValue(restricted);
        dest.writeValue(instrumental);
        dest.writeValue(explicit);
        dest.writeValue(lyricsBody);
        dest.writeValue(lyricsLanguage);
        dest.writeValue(lyricsLanguageDescription);
        dest.writeValue(scriptTrackingUrl);
        dest.writeValue(pixelTrackingUrl);
        dest.writeValue(htmlTrackingUrl);
        dest.writeValue(lyricsCopyright);
        dest.writeList(writerList);
        dest.writeList(publisherList);
        dest.writeValue(backlinkUrl);
        dest.writeValue(updatedTime);
    }

    public int describeContents() {
        return 0;
    }

}