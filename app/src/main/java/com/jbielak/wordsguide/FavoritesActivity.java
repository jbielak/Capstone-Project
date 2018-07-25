package com.jbielak.wordsguide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

    @BindView(R.id.rv_favorite_tracks)
    RecyclerView mRecyclerViewFavoriteTracks;

    private String mUserDisplayName;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTracksDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private ChildEventListener mChildEventListener;
    private List<TrackDto> mFavoriteTracks = new ArrayList<>();
    private TrackDtoAdapter mTrackDtoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

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

        attachDatabaseReadListener();

    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        mFavoriteTracks.clear();
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
                                new RemoveItemListener() {
                            @Override
                            public void onItemRemoved(String itemId, int position) {
                                removeFromFavorites(itemId, position);
                            }
                        });
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
