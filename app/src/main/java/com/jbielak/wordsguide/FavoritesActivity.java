package com.jbielak.wordsguide;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jbielak.wordsguide.adapter.TrackDtoAdapter;
import com.jbielak.wordsguide.dto.TrackDto;
import com.jbielak.wordsguide.network.MusixmatchApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {

    public static final String EXTRA_FAVORITE_TRACKS = "favorite_tracks";

    @BindView(R.id.rv_favorite_tracks)
    RecyclerView mRecyclerViewFavoriteTracks;

    private String mUserDisplayName;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTracksDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private ChildEventListener mChildEventListener;
    private List<TrackDto> mFavoriteTracks = new ArrayList<>();
    private TrackDtoAdapter mTrackDtoAdapter;
    private RemoveItemListener mRemoveItemListener;
    private int mLastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (!MusixmatchApiUtils.isOnline(this)) {
            Toast.makeText(this, getString(R.string.app_offline),
                    Toast.LENGTH_SHORT).show();
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserDisplayName = mFirebaseAuth.getCurrentUser().getDisplayName();
        mTracksDatabaseReference = mFirebaseDatabase.getReference()
                .child(TrackActivity.FAVORITE_TRACKS_KEY)
                .child(mUserDisplayName);
        mRemoveItemListener = new RemoveItemListener() {
            @Override
            public void onItemRemoved(String itemId, int position) {
                removeFromFavorites(itemId, position);
            }
        };

        if (savedInstanceState != null
                && savedInstanceState.containsKey(EXTRA_FAVORITE_TRACKS)) {
            mFavoriteTracks = savedInstanceState.getParcelableArrayList(EXTRA_FAVORITE_TRACKS);
            mTrackDtoAdapter = new TrackDtoAdapter(this, mFavoriteTracks, mRemoveItemListener);
            setupFavoriteTracksListView(mTrackDtoAdapter);
            ((LinearLayoutManager) mRecyclerViewFavoriteTracks.getLayoutManager())
                    .scrollToPosition(mLastFirstVisiblePosition);
        } else {
            attachDatabaseReadListener();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mTrackDtoAdapter.getItemCount() > 0) {
            outState.putParcelableArrayList(EXTRA_FAVORITE_TRACKS, mTrackDtoAdapter.getTrackList());
            mLastFirstVisiblePosition = ((LinearLayoutManager) mRecyclerViewFavoriteTracks
                    .getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        MenuItem item = menu.findItem(R.id.favorites);
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

    private void attachDatabaseReadListener() {

        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()) {

                        TrackDto track = dataSnapshot.getValue(TrackDto.class);
                        mFavoriteTracks.add(track);
                        mTrackDtoAdapter = new TrackDtoAdapter(getApplicationContext(), mFavoriteTracks,
                                mRemoveItemListener);
                        setupFavoriteTracksListView(mTrackDtoAdapter);
                    }
                }

                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };
        }
        mTracksDatabaseReference.addChildEventListener(mChildEventListener);
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mTracksDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void setupFavoriteTracksListView(TrackDtoAdapter trackdtoAdapter) {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewFavoriteTracks.setLayoutManager(layoutManager);
        mRecyclerViewFavoriteTracks.setAdapter(trackdtoAdapter);
    }

    public void removeFromFavorites(String trackId, int position) {
        if (mUserDisplayName != null && !mUserDisplayName.isEmpty()) {
            mTracksDatabaseReference.child(trackId).removeValue();
            updateRecyclerView(position);
            Toast.makeText(getApplicationContext(), getString(R.string.message_track_removed_from_favorites),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRecyclerView(int position) {
        mFavoriteTracks.remove(position);
        mRecyclerViewFavoriteTracks.removeViewAt(position);
        mTrackDtoAdapter.notifyItemRemoved(position);
        mTrackDtoAdapter.notifyItemRangeChanged(position,mFavoriteTracks.size());
    }

}
