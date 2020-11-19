package com.MusicFiles.Models.Options.BsaeClasses;

import android.net.Uri;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.util.ArrayList;
import java.util.Objects;

public abstract class BaseExtractorOptions {
    public ArrayList<String> path;
    public boolean cover;
    public Uri coverFolder;
    public int coverSizeLimit;
    public int coverWidth;

    public BaseExtractorOptions(ReadableMap options) {

        this.path = new ArrayList<>();
        if (options.hasKey("path")) {
            if (options.getType("path") == ReadableType.String) {
                this.path.add(options.getString("path"));
            } else if (options.getType("path") == ReadableType.Array) {
                ReadableArray arr = options.getArray("path");
                for (int i = 0; i < Objects.requireNonNull(arr).size(); i++) {
                    if (arr.getType(i) == ReadableType.String) {
                        this.path.add(arr.getString(i));
                    }
                }
            }
        }
        this.cover = options.hasKey("cover") && options.getBoolean("cover");
        this.coverFolder = options.hasKey("coverFolder") ? Uri.parse(options.getString("coverFolder")) : null;
        this.coverSizeLimit = options.hasKey("coverSizeLimit") ? options.getInt("coverSizeLimit") : 0;
        this.coverWidth = options.hasKey("coverWidth") ? options.getInt("coverWidth") : 0;

    }
}
