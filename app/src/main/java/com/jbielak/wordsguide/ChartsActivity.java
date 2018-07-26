package com.jbielak.wordsguide;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.TrackList;
import com.jbielak.wordsguide.model.charts.ChartsResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartsActivity extends AppCompatActivity {

    private static final String TAG = ChartsActivity.class.getSimpleName();

    @BindView(R.id.tv_charts_info)
    TextView mTextViewChartInfo;

    @BindView(R.id.rv_top_tracks)
    RecyclerView mRecyclerViewTopTracks;

    private String currentCountryCode;
    private MusixmatchService mService;
    private List<TrackList> mTracks;
    private TrackAdapter mTrackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        ButterKnife.bind(this);

        currentCountryCode = getCountryCode();
        setupChartsInfo(currentCountryCode);

        mService = MusixmatchApiUtils.getMusixmatchService();

        if (!MusixmatchApiUtils.isOnline(this)) {
            Toast.makeText(this, getString(R.string.app_offline),
                    Toast.LENGTH_SHORT).show();
        }
        getCharts();

    }

    private static String getCountryCode() {
        String localeCountry;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            localeCountry = Resources.getSystem().getConfiguration()
                    .getLocales().get(0).getCountry().toLowerCase();
        } else {
            localeCountry = Resources.getSystem().getConfiguration()
                    .locale.getCountry().toLowerCase();
        }
        return localeCountry;
    }

    private void getCharts() {
        mService.getCharts(MusixmatchApiUtils.API_KEY_VALUE,
                MusixmatchApiUtils.PAGE_SIZE_DEFAULT_VALUE,
                null, currentCountryCode,
                MusixmatchApiUtils.HAS_LYRICS_VALUE_DEFAULT)
                .enqueue(new Callback<ChartsResponse>() {

                    @Override
                    public void onResponse(Call<ChartsResponse> call, Response<ChartsResponse> response) {
                        Log.d(TAG, "Search request URL: " + call.request().url());
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Search response: " + response.body().toString());
                            if (response.body().getMessage().getBody().getTrackList().isEmpty()) {
                                Toast.makeText(getApplicationContext(),
                                        R.string.no_results, Toast.LENGTH_LONG).show();
                            } else {
                                mTracks = response.body().getMessage().getBody().getTrackList();
                                setupChartsList();
                            }
                        } else {
                            int statusCode = response.code();
                            Log.d(TAG, "Response unsuccessful - status code: " + statusCode);
                            // handle request errors depending on status code
                        }
                    }

                    @Override
                    public void onFailure(Call<ChartsResponse> call, Throwable t) {
                        Log.d(TAG, "Error loading from API: " + t.getMessage());
                    }
                });
    }

    @SuppressLint("StringFormatMatches")
    private void setupChartsInfo(String countryName) {
        mTextViewChartInfo.setText(getResources()
                .getString(R.string.charts_info,
                        String.valueOf(MusixmatchApiUtils.PAGE_SIZE_DEFAULT_VALUE), countryName));
    }

    private void setupChartsList() {
        mTrackAdapter = new TrackAdapter(this, mTracks);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTopTracks.setLayoutManager(layoutManager);
        mRecyclerViewTopTracks.setAdapter(mTrackAdapter);
    }
}
