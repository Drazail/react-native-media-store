package com.MusicFiles.Methods;

import com.MusicFiles.Models.Options.ExtractFilesInDirectoryOptions;
import com.MusicFiles.Utils.FS;
import com.MusicFiles.Utils.MetaDataExtractor;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extract {

    public static WritableMap extractFileMetaData(String path) {

        WritableMap results = new WritableNativeMap();
        HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(path);
        for (Map.Entry<String, String> entry : MetaMap.entrySet()) {
            results.putString(entry.getKey(), entry.getValue());
        }
        results.putString("lastModified", String.valueOf(new File(path).lastModified()));

        return results;
    }

    public static WritableMap extractFilesInDirectories(ExtractFilesInDirectoryOptions options) throws IOException, IllegalArgumentException  {
        WritableArray results = new WritableNativeArray();

        ArrayList<File> directories = new ArrayList<>();
        for (String dir : options.path) {
            File file = new File(dir);
            if (file.isDirectory()) {
                directories.add(new File(dir));
            }
        }

        WritableMap resultsMap = new WritableNativeMap();
        List<String> filesPaths = FS.listFilesInFolders(directories, options.minFileSize, options.maxFileSize, options.extensionFilter, options.sorted);
        resultsMap.putString("length", String.valueOf(filesPaths.size()));
        if (options.batchSize != 0) {
            filesPaths = filesPaths.subList(options.batchSize * options.batchNumber, Math.min(options.batchSize * (options.batchNumber + 1), filesPaths.size()));
        }
        for (String s : filesPaths) {
            WritableMap result = new WritableNativeMap();
            result.putString("path", s);
            result.putString("lastModified", String.valueOf(new File(s).lastModified()));
            if (options.metaData) {
                HashMap<String, String> MetaMap = MetaDataExtractor.getMetaData(s);
                for (Map.Entry<String, String> entry : MetaMap.entrySet()) {
                    result.putString(entry.getKey(), entry.getValue());
                }
            }
            if (options.cover) {
                String path = FS.saveCover(String.valueOf(options.coverFolder), s, options.coverSizeLimit, options.coverWidth);
                result.putString("cover", path);
            }
            results.pushMap(result);
        }
        resultsMap.putArray("results", results);
        return resultsMap;
    }
}
