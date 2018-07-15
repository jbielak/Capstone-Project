package com.jbielak.wordsguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jbielak.wordsguide.model.TrackSearchResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private MusixmatchService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        if (MusixmatchApiUtils.isOnline(this)) {
            mService = MusixmatchApiUtils.getMusixmatchService();
            findTrack("Redbone", null);
        } else {
            Toast.makeText(this, getString(R.string.app_offline),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void findTrack(String track, String artist) {
        mService.getTracks(MusixmatchApiUtils.API_KEY_VALUE, track, artist,
                MusixmatchApiUtils.HAS_LYRICS_VALUE_DEFAULT,
                MusixmatchApiUtils.SORT_BY_TRACK_RATING_VALUE_DEFAULT,
                null, null).enqueue(new Callback<TrackSearchResponse>() {

            @Override
            public void onResponse(Call<TrackSearchResponse> call, Response<TrackSearchResponse> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "Search response: " + response.body().toString());
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
}
