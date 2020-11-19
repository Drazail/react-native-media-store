package com.MusicFiles.Methods;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.MusicFiles.C;
import com.MusicFiles.Models.Options.GetSongsOptions;
import com.MusicFiles.Utils.FS;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.MusicFiles.Utils.GeneralUtils.LOG;
import static com.MusicFiles.Utils.OrderByGenerator.generateSortOrder;

public class GetSongs {

    public static WritableMap getSongs(GetSongsOptions options, ContentResolver contentResolver) throws UnsupportedOperationException {

        WritableArray jsonArray = new WritableNativeArray();

        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,//0
                MediaStore.Audio.Media.TITLE,//1
                MediaStore.Audio.Media.ARTIST,//2
                MediaStore.Audio.Media.ALBUM,//3
                // Media.DURATION requires Android Q or higher
                MediaStore.Audio.Media.DURATION,//4
                MediaStore.Audio.Media.SIZE,//5
                MediaStore.Audio.Media.DATA,//6
                MediaStore.Audio.Media.DATE_MODIFIED,//7
                MediaStore.Audio.Media.DATE_ADDED,//8
                MediaStore.Audio.AudioColumns.TRACK,//9
                MediaStore.Audio.AudioColumns.YEAR,//10
        };

        String Selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        List<String> selectionArgs = new ArrayList<>();

        if (options.allTexts != null) {
            String beginsWith = options.allTexts+"%";
            String wordBeginsWith = "% "+options.allTexts+"%";
            Selection += " AND ("
                    + MediaStore.Audio.Albums.ARTIST + " Like ? OR "
                    + MediaStore.Audio.Albums.ALBUM + " Like ? OR "
                    + MediaStore.Audio.Media.TITLE + " Like ? OR "
                    + MediaStore.Audio.Media.DATA + " Like ? OR "
                    + MediaStore.Audio.Albums.ARTIST + " Like ? OR "
                    + MediaStore.Audio.Albums.ALBUM + " Like ? OR "
                    + MediaStore.Audio.Media.TITLE + " Like ? OR "
                    + MediaStore.Audio.Media.DATA + " Like ?)";
            selectionArgs.addAll(Arrays.asList(
                    beginsWith, beginsWith, beginsWith, beginsWith,
                    wordBeginsWith, wordBeginsWith, wordBeginsWith, wordBeginsWith));
        }
        if (options.title != null) {
            Selection += " AND (" + MediaStore.Audio.Media.TITLE + " Like ? OR " + MediaStore.Audio.Media.TITLE + " Like ?)";
            selectionArgs.add(options.title + "%");
            selectionArgs.add("% "+options.title + "%");
        }
        if (options.artist != null) {
            Selection += " AND (" + MediaStore.Audio.Media.ARTIST + " Like ? OR " + MediaStore.Audio.Media.ARTIST + " Like ?)";
            selectionArgs.add(options.artist + "%");
            selectionArgs.add("% "+options.artist + "%");
        }
        if (options.album != null) {
            Selection += " AND (" + MediaStore.Audio.Media.ALBUM + " Like ? OR " + MediaStore.Audio.Media.ALBUM + " Like ?)";
            selectionArgs.add(options.album + "%");
            selectionArgs.add("% "+options.album + "%");
        }

        if (options.notTitle != null) {
            Selection += " AND " + MediaStore.Audio.Media.TITLE + " NOT LIKE ? ";
            selectionArgs.add(options.notTitle);
        }
        if (options.notArtist != null) {
            Selection += " AND " + MediaStore.Audio.Media.ARTIST + " NOT LIKE ? ";
            selectionArgs.add(options.notArtist);
        }
        if (options.notAlbum != null) {
            Selection += " AND " + MediaStore.Audio.Media.ALBUM + " NOT LIKE ? ";
            selectionArgs.add(options.notAlbum);
        }

        if (options.minDuration > 0) {
            Selection += " AND " + MediaStore.Audio.Media.DURATION + " >= " + options.minDuration;
        }
        if (options.maxDuration > 0) {
            Selection += " AND " + MediaStore.Audio.Media.DURATION + " <= " + options.maxDuration;
        }
        if (options.minSize > 0) {
            Selection += " AND " + MediaStore.Audio.Media.SIZE + " >= " + options.minSize;
        }
        if (options.maxSize > 0) {
            Selection += " AND " + MediaStore.Audio.Media.SIZE + " <= " + options.maxSize;
        }

        String orderBy = null;

        if (options.sortBy != null) {
            orderBy = generateSortOrder(options.sortBy, options.sortOrder);
        }

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, Selection, selectionArgs.size() != 0 ? selectionArgs.toArray(new String[0]) : null, orderBy);

        int cursorCount = Objects.requireNonNull(cursor).getCount();


        // todo: skip take in query (pagination improvements)
        // see https://stackoverflow.com/questions/10390577/limiting-number-of-rows-in-a-contentresolver-query-function
        if (cursorCount > (options.batchSize * options.batchNumber)) {
            cursor.moveToPosition(options.batchSize * options.batchNumber);
            do {

                WritableMap item = new WritableNativeMap();
                item.putString("id", cursor.getString(0));
                item.putString("title", cursor.getString(1));
                item.putString("artist", cursor.getString(2));
                item.putString("album", cursor.getString(3));
                item.putString("duration", cursor.getString(4));
                item.putString("size", cursor.getString(5));
                String path = cursor.getString(6);
                item.putString("path", path);
                item.putString("lastModified", cursor.getString(7));
                item.putString("dateAdded", cursor.getString(8));
                item.putString("track", cursor.getString(9));
                item.putString("year", cursor.getString(10));

                if (options.cover) {
                    try {
                        String coverPath = FS.saveCover(options.coverFolder, path, options.coverSizeLimit, options.coverWidth );
                        item.putString("cover", coverPath);
                    } catch (Exception e) {
                        Log.e(LOG, String.valueOf(e));
                        item.putString("cover", null);
                    }
                }

                jsonArray.pushMap(item);
            } while ((options.batchSize == 0 || cursor.getPosition() + 1 < options.batchSize * (options.batchNumber + 1)) & cursor.moveToNext());
        } else {
            cursor.close();
            throw new UnsupportedOperationException(C.ErrorEnum.EMPTY_CURSOR.toString());
        }
        cursor.close();
        WritableMap results = new WritableNativeMap();
        results.putInt("length", cursorCount);
        results.putArray("results", jsonArray);
        return results;
    }

}
