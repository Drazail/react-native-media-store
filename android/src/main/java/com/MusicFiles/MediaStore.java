package com.MusicFiles;

import androidx.annotation.NonNull;

import com.MusicFiles.Module.CoverImage;
import com.MusicFiles.Module.MusicFilesModule;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaStore implements ReactPackage {

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        return Collections.<NativeModule>singletonList(new MusicFilesModule(reactContext));
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return null;
    }


    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        List<ViewManager> viewManagers = new ArrayList<>();
        viewManagers.add(new CoverImage());
        return viewManagers;
    }
}
