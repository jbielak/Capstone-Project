package com.jbielak.wordsguide.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jbielak.wordsguide.BuildConfig;

public abstract class MusixmatchApiUtils {

    private static final String TAG = MusixmatchApiUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.musixmatch.com/ws/1.1/";

    public static final String METHOD_TRACK_SEARCH = "track.search?";

    public static final String API_KEY = "apikey";
    public static final String API_KEY_VALUE = BuildConfig.MUSIXMATCH_API_KEY;
    public static final String TRACK_KEY = "q_track";
    public static final String ARTIST_KEY = "q_artist";
    public static final String HAS_LYRICS_KEY = "f_has_lyrics";
    public static final int HAS_LYRICS_VALUE_DEFAULT = 1;
    public static final String SORT_BY_TRACK_RATING_KEY = "s_track_rating";
    public static final String SORT_BY_TRACK_RATING_VALUE_DEFAULT = "desc";
    public static final String PAGE_SIZE_KEY = "page_size";
    public static final int PAGE_SIZE_DEFAULT_VALUE = 30;
    public static final String PAGE_KEY = "page";

    public static final String METHOD_TRACK_LYRICS_GET = "track.lyrics.get?";
    public static final String TRACK_ID = "track_id";

    public static MusixmatchService getMusixmatchService() {
        return RetrofitClient.getClient(BASE_URL).create(MusixmatchService.class);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean isOnline = netInfo != null && netInfo.isConnectedOrConnecting();

        Log.i(TAG, "Connection provided: " + isOnline);

        return isOnline;
    }
}
