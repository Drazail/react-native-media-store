package com.MusicFiles.Models.Options;

import com.MusicFiles.Models.Options.BsaeClasses.BaseExtractorOptions;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

public class ExtractFileMetadataOptions extends BaseExtractorOptions {

    public ExtractFileMetadataOptions(ReadableMap options) throws IllegalArgumentException {
        super(options);
        if (options.getType("path") != ReadableType.String) {
            throw new IllegalArgumentException("path should be of type ReadableType.String");
        }
    }
}
