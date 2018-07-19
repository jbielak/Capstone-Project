package com.jbielak.wordsguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.jbielak.wordsguide.model.TrackList;
import com.jbielak.wordsguide.model.TrackSearchResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    public static final String EXTRA_TRACK_LIST = "com.jbielak.wordsguide.model.TrackList";

    @BindView(R.id.et_search_track)
    protected EditText mTrack;

    @BindView(R.id.et_search_artist)
    protected EditText mArtist;

    private MusixmatchService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mService = MusixmatchApiUtils.getMusixmatchService();

        if (!MusixmatchApiUtils.isOnline(this)) {
            Toast.makeText(this, getString(R.string.app_offline),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_search)
    protected void searchTrack() {
        if (mTrack.getText() == null || mTrack.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.no_track_specified), Toast.LENGTH_SHORT)
                    .show();
        } else {
            String track = mTrack.getText().toString().trim();
            boolean isArtistNullOrEmpty =  mArtist.getText() == null
                    || mArtist.getText().toString().isEmpty();
            String artist = isArtistNullOrEmpty ? null : mArtist.getText().toString().trim();
            findTrack(track, artist);
        }

    }

    private void findTrack(String track, String artist) {
        mService.getTracks(MusixmatchApiUtils.API_KEY_VALUE, track, artist,
                MusixmatchApiUtils.HAS_LYRICS_VALUE_DEFAULT,
                MusixmatchApiUtils.SORT_BY_TRACK_RATING_VALUE_DEFAULT,
                MusixmatchApiUtils.PAGE_SIZE_DEFAULT_VALUE,null)
                .enqueue(new Callback<TrackSearchResponse>() {

            @Override
            public void onResponse(Call<TrackSearchResponse> call, Response<TrackSearchResponse> response) {
                Log.d(TAG, "Search request URL: " + call.request().url());
                if (response.isSuccessful()) {
                    Log.d(TAG, "Search response: " + response.body().toString());
                    if (response.body().getMessage().getHeader().getAvailable() == 0) {
                        Toast.makeText(getApplicationContext(),
                                R.string.no_results, Toast.LENGTH_LONG).show();
                    } else {
                        showResults(response);
                    }
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "Response unsuccessful - status code: " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<TrackSearchResponse> call, Throwable t) {
                Log.d(TAG, "Error loading from API: " + t.getMessage());
            }
        });
    }

    private void showResults(Response<TrackSearchResponse> response) {
        ArrayList<TrackList> tracksFound = (ArrayList<TrackList>) response.body().getMessage().getBody().getTrackList();
        Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
        searchResultsIntent.putParcelableArrayListExtra(EXTRA_TRACK_LIST, tracksFound);
        startActivity(searchResultsIntent);
    }
}
