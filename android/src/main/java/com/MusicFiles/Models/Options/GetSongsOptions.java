package com.MusicFiles.Models.Options;


import com.MusicFiles.Models.Options.BsaeClasses.BaseOptions;
import com.facebook.react.bridge.ReadableMap;

public class GetSongsOptions extends BaseOptions {


    public String allTexts;
    public String title;
    public String artist;
    public String album;

    public String notTitle;
    public String notArtist;
    public String notAlbum;

    public int minDuration;
    public int maxDuration;
    public int minSize;
    public int maxSize;

    public boolean cover;
    public String coverFolder;
    public int coverSizeLimit;
    public int coverWidth;


    public GetSongsOptions(ReadableMap options) {
        super(options);

        this.allTexts = options.hasKey("allTexts") ? options.getString("allTexts") : null;
        this.title = options.hasKey("title") ? options.getString("title") : null;
        this.artist = options.hasKey("artist") ? options.getString("artist") : null;
        this.album = options.hasKey("album") ? options.getString("album") : null;
        this.notTitle = options.hasKey("notTitle") ? options.getString("notTitle") : null;
        this.notArtist = options.hasKey("notArtist") ? options.getString("notArtist") : null;
        this.notAlbum = options.hasKey("notAlbum") ? options.getString("notAlbum") : null;
        this.minDuration = options.hasKey("minDuration") ? options.getInt("minDuration") : 0;
        this.maxDuration = options.hasKey("maxDuration") ? options.getInt("maxDuration") : 0;
        this.minSize = options.hasKey("minSize") ? options.getInt("minSize") : 0;
        this.maxSize = options.hasKey("maxSize") ? options.getInt("maxSize") : 0;

        this.cover = options.hasKey("cover") && options.getBoolean("cover");
        this.coverFolder = options.hasKey("coverFolder") ? options.getString("coverFolder") : null;
        this.coverSizeLimit = options.hasKey("coverSizeLimit") ? options.getInt("coverSizeLimit") : 0;
        this.coverWidth = options.hasKey("coverWidth") ? options.getInt("coverWidth") : 0;

    }
}
