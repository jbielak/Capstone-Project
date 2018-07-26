package com.jbielak.wordsguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
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
    private int mLastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(SearchActivity.EXTRA_TRACK_LIST)) {
            mTracks = savedInstanceState.getParcelableArrayList(SearchActivity.EXTRA_TRACK_LIST);
            mTrackAdapter = new TrackAdapter(this, mTracks);
            setupTracksListView(mTrackAdapter);
            ((LinearLayoutManager) mRecyclerViewTracks.getLayoutManager())
                    .scrollToPosition(mLastFirstVisiblePosition);
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
            mLastFirstVisiblePosition = ((LinearLayoutManager) mRecyclerViewTracks
                    .getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
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

    private void setupTracksListView(TrackAdapter trackAdapter) {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTracks.setLayoutManager(layoutManager);
        mRecyclerViewTracks.setAdapter(trackAdapter);
    }
}
