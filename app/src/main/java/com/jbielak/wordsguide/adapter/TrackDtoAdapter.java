package com.jbielak.wordsguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbielak.wordsguide.FavoritesActivity;
import com.jbielak.wordsguide.R;
import com.jbielak.wordsguide.RemoveItemListener;
import com.jbielak.wordsguide.TrackActivity;
import com.jbielak.wordsguide.converter.TrackConverter;
import com.jbielak.wordsguide.dto.TrackDto;
import com.jbielak.wordsguide.widget.WordsGuideWidgetService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackDtoAdapter extends RecyclerView.Adapter<TrackDtoAdapter.ViewHolder> {

    private Context mContext;
    private List<TrackDto> mTrackList;
    private RemoveItemListener mRemoveItemListener;

    public TrackDtoAdapter(Context context, List<TrackDto> tracks, RemoveItemListener listener) {
        mContext = context;
        mTrackList = tracks;
        mRemoveItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorite_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mTrackList != null && !mTrackList.isEmpty()) {
            final TrackDto track = mTrackList.get(position);
            holder.mTitleTextView.setText(track.getTrackName());
            holder.mArtistTextView.setText(track.getArtistName());

            if (track.getAlbumCoverart100x100() != null && !track.getAlbumCoverart100x100().isEmpty()) {
                Picasso.get()
                        .load(track.getAlbumCoverart100x100())
                        .resize(100,100)
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .error(R.drawable.ic_broken_image_black_24dp)
                        .into(holder.mCoverImageView);

            }

            holder.mBtnRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRemoveItemListener != null) {
                        int position = mTrackList.indexOf(track);
                        mRemoveItemListener.onItemRemoved(String.valueOf(track.getTrackId()), position);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent trackIntent = new Intent(mContext, TrackActivity.class);
                    WordsGuideWidgetService.startActionUpdateTrackWidgets(mContext,
                            TrackConverter.toTrack(track));
                    trackIntent.putExtra(TrackAdapter.EXTRA_TRACK, TrackConverter.toTrack(track));
                    trackIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        @BindView(R.id.btn_remove)
        ImageButton mBtnRemoveFromFavorites;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setTrackList(List<TrackDto> trackList) {
        mTrackList = trackList;
    }

    public ArrayList<TrackDto> getTrackList() {
        return (ArrayList<TrackDto>) mTrackList;
    }
}
