package com.jbielak.wordsguide;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.TrackList;
import com.jbielak.wordsguide.model.charts.ChartsResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;

import java.io.IOException;
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
    private static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 2;

    @BindView(R.id.tv_charts_info)
    TextView mTextViewChartInfo;

    @BindView(R.id.rv_top_tracks)
    RecyclerView mRecyclerViewTopTracks;

    private LocationManager mLocationManager;
    private Location mCurrentLocation;
    private LocationListener mLocationListener;
    private String mCurrentCountry;

    private Locale mCurrentLocale;
    private MusixmatchService mService;
    private List<TrackList> mTracks;
    private TrackAdapter mTrackAdapter;
    private int mLastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setupChartsDataFromLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };

        if (ContextCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationManager
                    .requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mLocationManager
                        .requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);
            }

        }

        mCurrentLocale = getDeviceLocale();
        mService = MusixmatchApiUtils.getMusixmatchService();

        if (!MusixmatchApiUtils.isOnline(this)) {
            Toast.makeText(this, getString(R.string.app_offline),
                    Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_TOP_TRACKS)) {
            mTracks = savedInstanceState.getParcelableArrayList(EXTRA_TOP_TRACKS);
            mTrackAdapter = new TrackAdapter(this, mTracks);
            setupChartsInfo(mCurrentLocale);
            setupChartsList();
            ((LinearLayoutManager) mRecyclerViewTopTracks.getLayoutManager())
                    .scrollToPosition(mLastFirstVisiblePosition);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        MenuItem item = menu.findItem(R.id.charts);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                AuthUI.getInstance().signOut(getApplicationContext());
                finish();
                return true;
            case R.id.app_home:
                Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.search:
                Intent intentSearch = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intentSearch);
                return true;
            case R.id.favorites:
                Intent intentFavorites = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(intentFavorites);
                return true;
            case R.id.charts:
                Intent intentCharts = new Intent(getApplicationContext(), ChartsActivity.class);
                startActivity(intentCharts);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void getCharts(String countryCode, final String countryName) {
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
                                setupChartsInfo(countryName);
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
                                getCharts(country.getCode().toLowerCase(), country.getName());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("gps", "Location permission granted");
                    try {
                        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null);
                    } catch (SecurityException ex) {
                        Log.d("gps", "Location permission did not work");
                    }
                } else {
                    String countryName = getCountryNameInEnglish(mCurrentLocale) == null
                            ? mCurrentLocale.getDisplayCountry() : getCountryNameInEnglish(mCurrentLocale);
                    getCharts(mCurrentLocale.getCountry().toLowerCase(), countryName);
                }
                break;
        }
    }

    private void setupChartsDataFromLocation(Location location){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> address;
        String locationCountry;
        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationCountry = address.get(0).getCountryName();
            if (mCurrentCountry == null || !mCurrentCountry.equals(locationCountry)) {
                mCurrentCountry = locationCountry;
                setupChartsInfo(mCurrentCountry);
                getCharts(address.get(0).getCountryCode().toLowerCase(), mCurrentCountry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
