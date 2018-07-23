package com.jbielak.wordsguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.Track;
import com.jbielak.wordsguide.model.TrackSearchResponse;
import com.jbielak.wordsguide.model.lyrics.LyricsResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackActivity extends AppCompatActivity {

    private static final String TAG = TrackActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_artist)
    TextView mArtist;

    @BindView(R.id.tv_album)
    TextView mAlbum;

    @BindView(R.id.tv_lyrics)
    TextView mLyrics;

    private Track mTrack;
    private MusixmatchService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ButterKnife.bind(this);

        mService = MusixmatchApiUtils.getMusixmatchService();

        if (getIntent().hasExtra(TrackAdapter.EXTRA_TRACK)) {
            mTrack = getIntent().getParcelableExtra(TrackAdapter.EXTRA_TRACK);

            mTitle.setText(mTrack.getTrackName());
            mArtist.setText(mTrack.getArtistName());
            mAlbum.setText(String.format("%s, %s",
                    mTrack.getAlbumName(),
                    mTrack.getFirstReleaseDate().substring(0, 4)));

            if (mTrack.getTrackId() != null) {
                getLyrics(String.valueOf(mTrack.getTrackId()));
            }
        }
    }

    private void getLyrics(String trackId) {
        mService.getLyrics(MusixmatchApiUtils.API_KEY_VALUE, trackId)
                .enqueue(new Callback<LyricsResponse>() {

                    @Override
                    public void onResponse(Call<LyricsResponse> call, Response<LyricsResponse> response) {
                        Log.d(TAG, "Search request URL: " + call.request().url());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "Search response: " + response.body().toString());
                            mLyrics.setText(response.body().getMessage().getBody().getLyrics().getLyricsBody());
                        } else {
                            int statusCode = response.code();
                            Log.d(TAG, "Response unsuccessful - status code: " + statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<LyricsResponse> call, Throwable t) {
                        Log.d(TAG, "Search request URL: " + call.request().url());
                        Log.d(TAG, "Error loading from API: " + t.getMessage());
                    }
                });
    }
}
