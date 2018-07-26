package com.jbielak.wordsguide.network;

import com.jbielak.wordsguide.ChartsActivity;
import com.jbielak.wordsguide.model.TrackSearchResponse;
import com.jbielak.wordsguide.model.charts.ChartsResponse;
import com.jbielak.wordsguide.model.lyrics.LyricsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusixmatchService {

    @GET(MusixmatchApiUtils.METHOD_TRACK_SEARCH)
    Call<TrackSearchResponse> getTracks(
                    @Query(MusixmatchApiUtils.API_KEY) String apiKey,
                    @Query(MusixmatchApiUtils.TRACK_KEY) String track,
                    @Query(MusixmatchApiUtils.ARTIST_KEY) String artist,
                    @Query(MusixmatchApiUtils.HAS_LYRICS_KEY) Integer hasLyrics,
                    @Query(MusixmatchApiUtils.SORT_BY_TRACK_RATING_KEY) String sortByTrackRating,
                    @Query(MusixmatchApiUtils.PAGE_SIZE_KEY) Integer pageSize,
                    @Query(MusixmatchApiUtils.PAGE_KEY) Integer page);


    @GET(MusixmatchApiUtils.METHOD_TRACK_LYRICS_GET)
    Call<LyricsResponse> getLyrics(
            @Query(MusixmatchApiUtils.API_KEY) String apiKey,
            @Query(MusixmatchApiUtils.TRACK_ID) String trackId);

    @GET(MusixmatchApiUtils.METHOD_CHART_TRACKS_GET)
    Call<ChartsResponse> getCharts(
            @Query(MusixmatchApiUtils.API_KEY) String apiKey,
            @Query(MusixmatchApiUtils.PAGE_SIZE_KEY) Integer pageSize,
            @Query(MusixmatchApiUtils.PAGE_KEY) Integer page,
            @Query(MusixmatchApiUtils.COUNTRY_KEY) String country,
            @Query(MusixmatchApiUtils.HAS_LYRICS_KEY) Integer hasLyrics);
}
