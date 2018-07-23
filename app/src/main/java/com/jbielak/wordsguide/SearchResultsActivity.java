package com.jbielak.wordsguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.TrackList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.rv_tracks)
    RecyclerView mRecyclerViewTracks;

    private List<TrackList> mTracks;
    private TrackAdapter mTrackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(SearchActivity.EXTRA_TRACK_LIST)) {
            mTracks = savedInstanceState.getParcelableArrayList(SearchActivity.EXTRA_TRACK_LIST);
            mTrackAdapter = new TrackAdapter(this, mTracks);
            setupTracksListView(mTrackAdapter);
        } else {
            if (getIntent().hasExtra(SearchActivity.EXTRA_TRACK_LIST)) {
                mTracks = getIntent().getParcelableArrayListExtra(SearchActivity.EXTRA_TRACK_LIST);
                mTrackAdapter = new TrackAdapter(this, mTracks);
                setupTracksListView(mTrackAdapter);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mTrackAdapter.getItemCount() > 0) {
            outState.putParcelableArrayList(SearchActivity.EXTRA_TRACK_LIST,
                    mTrackAdapter.getTrackList());
        }
        super.onSaveInstanceState(outState);
    }

    private void setupTracksListView(TrackAdapter trackAdapter) {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTracks.setLayoutManager(layoutManager);
        mRecyclerViewTracks.setAdapter(trackAdapter);
    }
}
