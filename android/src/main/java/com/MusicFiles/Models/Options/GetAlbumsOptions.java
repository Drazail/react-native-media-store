package com.MusicFiles.Models.Options;

import com.MusicFiles.Models.Options.BsaeClasses.BaseOptions;
import com.facebook.react.bridge.ReadableMap;

public class GetAlbumsOptions extends BaseOptions {
    public String artist;
    public String album;

    public GetAlbumsOptions(ReadableMap options) {
        super(options);
        this.artist = options.hasKey("artist") ? options.getString("artist") : null;
        this.album = options.hasKey("album") ? options.getString("album") : null;
    }
}
