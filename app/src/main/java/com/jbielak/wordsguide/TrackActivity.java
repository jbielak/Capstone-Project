package com.jbielak.wordsguide;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.dto.TrackDto;
import com.jbielak.wordsguide.model.Track;
import com.jbielak.wordsguide.model.lyrics.LyricsResponse;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;
import com.jbielak.wordsguide.network.MusixmatchService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackActivity extends AppCompatActivity {

    private static final String TAG = TrackActivity.class.getSimpleName();

    public static final String FAVORITE_TRACKS_KEY = "favorite_tracks";

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_artist)
    TextView mArtist;

    @BindView(R.id.tv_album)
    TextView mAlbum;

    @BindView(R.id.tv_lyrics)
    TextView mLyrics;

    @BindView(R.id.fab_share)
    FloatingActionButton mFabShare;

    private Track mTrack;
    private MusixmatchService mService;

    private String mUserDisplayName;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTracksDatabaseReference;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mService = MusixmatchApiUtils.getMusixmatchService();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserDisplayName = mFirebaseAuth.getCurrentUser().getDisplayName();
        mTracksDatabaseReference = mFirebaseDatabase.getReference()
                .child(FAVORITE_TRACKS_KEY)
                .child(mUserDisplayName);

        if (getIntent().hasExtra(TrackAdapter.EXTRA_TRACK)) {
            mTrack = getIntent().getParcelableExtra(TrackAdapter.EXTRA_TRACK);

            mTitle.setText(mTrack.getTrackName());
            mArtist.setText(mTrack.getArtistName());
            String firstReleaseDate = mTrack.getFirstReleaseDate();
            if (firstReleaseDate != null && firstReleaseDate.length() >= 4) {
                firstReleaseDate = firstReleaseDate.substring(0, 4);
            } else {
                firstReleaseDate = "";
            }
            mAlbum.setText(String.format("%s, %s",
                    mTrack.getAlbumName(), firstReleaseDate));

            if (mTrack.getTrackId() != null) {
                getLyrics(String.valueOf(mTrack.getTrackId()));
            }
        }

        mFabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserDisplayName != null && !mUserDisplayName.isEmpty()) {
                    String trackToShare = mTrack.getTrackShareUrl();
                    if (trackToShare!= null && !trackToShare.isEmpty()) {
                        shareTrack(trackToShare);
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab_add_to_favorites)
    protected void addToFavorites() {
        if (mUserDisplayName != null && !mUserDisplayName.isEmpty()) {
            TrackDto track = new TrackDto(
                    mTrack.getTrackId(),
                    mTrack.getTrackName(),
                    mTrack.getArtistName(),
                    mTrack.getAlbumName(),
                    mTrack.getFirstReleaseDate(),
                    mTrack.getAlbumCoverart100x100(),
                    mTrack.getTrackShareUrl());
            mTracksDatabaseReference.child(String.valueOf(track.getTrackId())).setValue(track);
            Toast.makeText(getApplicationContext(), getString(R.string.message_track_added_to_favorites),
                    Toast.LENGTH_SHORT).show();
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

    private void shareTrack(String trackUri) {
        String mimeType = "text/plain";
        String title = getResources().getString(R.string.share_track);

        ShareCompat.IntentBuilder.from(this)
                .setChooserTitle(title)
                .setType(mimeType)
                .setText(trackUri)
                .startChooser();
    }
}
