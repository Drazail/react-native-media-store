package com.MusicFiles.Models.Options;

import com.MusicFiles.Models.Options.BsaeClasses.BaseExtractorOptions;
import com.facebook.react.bridge.ReadableMap;

public class ExtractFilesInDirectoryOptions extends BaseExtractorOptions {

    public int minFileSize;
    public int maxFileSize;
    public String extensionFilter;

    public boolean sorted;
    public int batchSize;
    public int batchNumber;
    public boolean metaData;

    public ExtractFilesInDirectoryOptions(ReadableMap options) {
        super(options);
        this.minFileSize = options.hasKey("minFileSize") ? options.getInt("minFileSize") : 0;
        this.maxFileSize = options.hasKey("maxFileSize") ? options.getInt("maxFileSize") : 1073741824;
        this.extensionFilter = options.hasKey("extensionFilter") ? options.getString("extensionFilter") : "";
        this.metaData = options.hasKey("metaData") && options.getBoolean("metaData");

        this.sorted = options.hasKey("sorted") && options.getBoolean("sorted");
        this.batchSize = options.hasKey("batchSize") ? options.getInt("batchSize") : 0;
        this.batchNumber = options.hasKey("batchNumber") ? options.getInt("batchNumber") : 0;
    }
}
