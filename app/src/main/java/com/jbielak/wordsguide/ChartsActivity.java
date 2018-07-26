package com.jbielak.wordsguide;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.TrackList;
import com.jbielak.wordsguide.model.charts.ChartsResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartsActivity extends AppCompatActivity {

    public static final String EXTRA_TOP_TRACKS = "top_tracks";

    private static final String TAG = ChartsActivity.class.getSimpleName();

    @BindView(R.id.tv_charts_info)
    TextView mTextViewChartInfo;

    @BindView(R.id.rv_top_tracks)
    RecyclerView mRecyclerViewTopTracks;

    private Locale currentLocale;
    private MusixmatchService mService;
    private List<TrackList> mTracks;
    private TrackAdapter mTrackAdapter;
    private int mLastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        ButterKnife.bind(this);

        currentLocale = getDeviceLocale();
        setupChartsInfo(currentLocale);

        mService = MusixmatchApiUtils.getMusixmatchService();

        if (!MusixmatchApiUtils.isOnline(this)) {
            Toast.makeText(this, getString(R.string.app_offline),
                    Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_TOP_TRACKS)) {
            mTracks = savedInstanceState.getParcelableArrayList(EXTRA_TOP_TRACKS);
            mTrackAdapter = new TrackAdapter(this, mTracks);
            setupChartsInfo(currentLocale);
            setupChartsList();
            ((LinearLayoutManager) mRecyclerViewTopTracks.getLayoutManager())
                    .scrollToPosition(mLastFirstVisiblePosition);
        } else {
            getCharts(currentLocale.getCountry().toLowerCase());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mTrackAdapter.getItemCount() > 0) {
            outState.putParcelableArrayList(EXTRA_TOP_TRACKS, mTrackAdapter.getTrackList());
            mLastFirstVisiblePosition = ((LinearLayoutManager) mRecyclerViewTopTracks
                    .getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        super.onSaveInstanceState(outState);
    }

    private static Locale getDeviceLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem().getConfiguration()
                    .getLocales().get(0);
        } else {
            locale = Resources.getSystem().getConfiguration().locale;
        }
        return locale;
    }

    private void getCharts(String countryCode) {
        mService.getCharts(MusixmatchApiUtils.API_KEY_VALUE,
                MusixmatchApiUtils.PAGE_SIZE_DEFAULT_VALUE,
                null, countryCode,
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

    @OnClick(R.id.btn_select_country)
    protected void selectCountry () {
        CountryPicker countryPicker =
                new CountryPicker.Builder().with(getApplicationContext())
                        .listener(new OnCountryPickerListener() {
                            @Override public void onSelectCountry(Country country) {
                                setupChartsInfo(country.getName());
                                getCharts(country.getCode().toLowerCase());
                            }
                        })
                        .build();
        countryPicker.showDialog(getSupportFragmentManager());
    }

    @SuppressLint("StringFormatMatches")
    private void setupChartsInfo(Locale locale) {
        String country = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? getCountryNameInEnglish(locale) : locale.getDisplayCountry();
        mTextViewChartInfo.setText(getResources()
                .getString(R.string.charts_info,
                        String.valueOf(MusixmatchApiUtils.PAGE_SIZE_DEFAULT_VALUE), country));
    }

    private void setupChartsInfo(String country) {
        mTextViewChartInfo.setText(getResources()
                .getString(R.string.charts_info,
                        String.valueOf(MusixmatchApiUtils.PAGE_SIZE_DEFAULT_VALUE), country));
    }

    private void setupChartsList() {
        mTrackAdapter = new TrackAdapter(this, mTracks);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTopTracks.setLayoutManager(layoutManager);
        mRecyclerViewTopTracks.setAdapter(mTrackAdapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getCountryNameInEnglish(Locale locale) {
        Locale english = new Locale.Builder().setLanguage("en").build();
        return locale.getDisplayCountry(english);
    }
}
