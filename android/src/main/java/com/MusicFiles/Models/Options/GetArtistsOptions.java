package com.MusicFiles.Models.Options;

import com.MusicFiles.Models.Options.BsaeClasses.BaseOptions;
import com.facebook.react.bridge.ReadableMap;

public class GetArtistsOptions extends BaseOptions {
    public String artist;

    public GetArtistsOptions(ReadableMap options) {
        super(options);
        this.artist = options.hasKey("artist") ? options.getString("artist") : null;
    }
}
