package com.jbielak.wordsguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.Track;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_artist)
    TextView mArtist;

    @BindView(R.id.tv_album)
    TextView mAlbum;

    private Track mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(TrackAdapter.EXTRA_TRACK)) {
            mTrack = getIntent().getParcelableExtra(TrackAdapter.EXTRA_TRACK);

            mTitle.setText(mTrack.getTrackName());
            mArtist.setText(mTrack.getArtistName());
            mAlbum.setText(String.format("%s, %s",
                    mTrack.getAlbumName(),
                    mTrack.getFirstReleaseDate().substring(0, 4)));
        }
    }
}
