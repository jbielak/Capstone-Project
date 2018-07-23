package com.jbielak.wordsguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbielak.wordsguide.R;
import com.jbielak.wordsguide.TrackActivity;
import com.jbielak.wordsguide.model.Track;
import com.jbielak.wordsguide.model.TrackList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    public static final String EXTRA_TRACK = "com.jbielak.wordsguide.model.Track";

    private Context mContext;
    private List<TrackList> mTrackList;

    public TrackAdapter(Context context, List<TrackList> tracks) {
        mContext = context;
        mTrackList = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrackList != null && !mTrackList.isEmpty()) {
            final Track track = mTrackList.get(position).getTrack();
            holder.mTitleTextView.setText(track.getTrackName());
            holder.mArtistTextView.setText(track.getArtistName());

            if (track.getAlbumCoverart100x100() != null && !track.getAlbumCoverart100x100().isEmpty()) {
                Picasso.get()
                        .load(track.getAlbumCoverart100x100())
                        .resize(100,100)
                        .into(holder.mCoverImageView);

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent trackIntent = new Intent(mContext, TrackActivity.class);
                    trackIntent.putExtra(EXTRA_TRACK, track);
                    mContext.startActivity(trackIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView mCoverImageView;
        @BindView(R.id.tv_title)
        TextView mTitleTextView;
        @BindView(R.id.tv_artist)
        TextView mArtistTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setTrackList(List<TrackList> trackList) {
        mTrackList = trackList;
    }

    public ArrayList<TrackList> getTrackList() {
        return (ArrayList<TrackList>) mTrackList;
    }
}
