package com.jbielak.wordsguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jbielak.wordsguide.model.TrackList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.tv_results)
    TextView mTextViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);


        List<TrackList> tracksFound;
        if (getIntent().hasExtra(SearchActivity.EXTRA_TRACK_LIST)) {
            tracksFound = getIntent().getParcelableArrayListExtra(SearchActivity.EXTRA_TRACK_LIST);
            mTextViewResults.setText(tracksFound.toString());
        }
    }
}
