package com.MusicFiles.Utils;

import android.media.MediaMetadataRetriever;

import java.util.HashMap;

public class MetaDataExtractor {

    public static byte[] getEmbeddedPicture(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        byte[] embeddedPicture = mmr.getEmbeddedPicture();
        mmr.release();
        return  embeddedPicture;
    }

    public static HashMap<String, String> getMetaData(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        HashMap <String, String> TrackMap = new HashMap<>();

        TrackMap.put("artist", mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        TrackMap.put("album", mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        TrackMap.put("duration", mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        TrackMap.put("title", mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));

        return TrackMap;
    }
}
