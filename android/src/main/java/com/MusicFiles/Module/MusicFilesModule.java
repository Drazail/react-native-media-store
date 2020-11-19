package com.MusicFiles.Module;

import android.content.ContentResolver;

import com.MusicFiles.Methods.Extract;
import com.MusicFiles.Methods.GetAlbums;
import com.MusicFiles.Methods.GetArtists;
import com.MusicFiles.Methods.GetSongs;
import com.MusicFiles.Models.Options.ExtractFileMetadataOptions;
import com.MusicFiles.Models.Options.ExtractFilesInDirectoryOptions;
import com.MusicFiles.Models.Options.GetAlbumsOptions;
import com.MusicFiles.Models.Options.GetArtistsOptions;
import com.MusicFiles.Models.Options.GetSongsOptions;
import com.MusicFiles.Utils.FS;
import com.MusicFiles.Utils.StaticExecutor;
import com.MusicFiles.Utils.ThreadRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.util.Objects;

public class MusicFilesModule extends ReactContextBaseJavaModule {

    public MusicFilesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MusicFiles";
    }

    @ReactMethod
    public void extractFileMetaData(ReadableMap args, Promise callback) {

        ThreadRunnable runnable = new ThreadRunnable(
                () -> {
                    try {
                        ExtractFileMetadataOptions options = new ExtractFileMetadataOptions(args);
                        WritableMap results = Extract.extractFileMetaData(String.valueOf(options.path));
                        if (options.cover) {
                            try {
                                // options.path.get(0) to returns the single file path passed from the JS thread
                                String PathToCover = FS.saveCover(String.valueOf(options.coverFolder), String.valueOf(options.path.get(0)), options.coverSizeLimit, options.coverWidth);
                                results.putString("cover", PathToCover);
                            } catch (Exception e) {
                                e.printStackTrace();
                                results.putNull("cover");
                            }
                        }
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.execute(StaticExecutor.service);

    }

    @ReactMethod
    public void extractFilesInDirectory(ReadableMap args, Promise callback) {

        ThreadRunnable runnable = new ThreadRunnable(
                () -> {
                    try {
                        ExtractFilesInDirectoryOptions options = new ExtractFilesInDirectoryOptions(args);
                        WritableMap results = Extract.extractFilesInDirectories(options);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.execute(StaticExecutor.service);

    }

    @ReactMethod
    public void getAlbums(ReadableMap args, Promise callback) {
        ThreadRunnable runnable = new ThreadRunnable(
                () -> {
                    try {
                        GetAlbumsOptions options = new GetAlbumsOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = GetAlbums.getAlbums(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.execute(StaticExecutor.service);
    }

    @ReactMethod
    public void getArtists(ReadableMap args, Promise callback) {

        ThreadRunnable runnable = new ThreadRunnable(
                () -> {
                    try {
                        GetArtistsOptions options = new GetArtistsOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = GetArtists.getArtists(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.execute(StaticExecutor.service);

    }

    @ReactMethod
    public void getSongs(ReadableMap args, Promise callback) {
        ThreadRunnable runnable = new ThreadRunnable(
                () -> {
                    try {
                        GetSongsOptions options = new GetSongsOptions(args);
                        ContentResolver contentResolver = Objects.requireNonNull(getCurrentActivity()).getContentResolver();
                        WritableMap results = GetSongs.getSongs(options, contentResolver);
                        callback.resolve(results);
                    } catch (Exception e) {
                        callback.reject(e);
                    }
                }
        );

        runnable.execute(StaticExecutor.service);

    }

    @ReactMethod
    public void changeThreadPoolSize(int ThreadPoolSize, Promise callback) {
        try {
            StaticExecutor.updateExecutorService(ThreadPoolSize);
            callback.resolve(null);
        } catch (Exception e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }
}
