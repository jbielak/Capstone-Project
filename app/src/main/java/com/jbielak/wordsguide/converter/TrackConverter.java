package com.jbielak.wordsguide.converter;

import com.jbielak.wordsguide.dto.TrackDto;
import com.jbielak.wordsguide.model.Track;

public abstract class TrackConverter {

    public static Track toTrack(TrackDto trackDto){
        Track track = new Track();
        track.setTrackId(trackDto.getTrackId());
        track.setTrackName(trackDto.getTrackName());
        track.setArtistName(trackDto.getArtistName());
        track.setAlbumName(trackDto.getAlbumName());
        track.setFirstReleaseDate(trackDto.getFirstReleaseDate());
        track.setAlbumCoverart100x100(trackDto.getAlbumCoverart100x100());
        track.setTrackShareUrl(trackDto.getTrackShareUrl());

        return track;
    }
}
