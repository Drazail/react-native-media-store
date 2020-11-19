package com.MusicFiles.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Arrays;

import androidx.annotation.NonNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import static com.MusicFiles.Utils.GeneralUtils.LOG;

public class FS {

    private static String SHAsum(byte[] byteArray){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byteArray2Hex(Objects.requireNonNull(md).digest(byteArray));
    }

    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static String saveCover(@NonNull String coverPath, String TrackPath, int sizeLimit, int width) throws IOException, IllegalArgumentException  {
        String sha = SHAsum(TrackPath.getBytes());
        String sub = sha.substring(sha.length() - 2);


        File baseDir = new File(coverPath);
        if(baseDir.exists() && !baseDir.isDirectory()){
            Log.e(LOG, "coverPath is a file");
            throw new IllegalArgumentException("coverPath is a file");
        }
        if(!baseDir.exists()){
            baseDir.mkdir();
        }

        File subDir = new File(coverPath + "/" + sub);
        if (subDir.exists() && ! subDir.isDirectory()) {
            subDir.delete();
        }
        if (! subDir.exists()) {
            subDir.mkdir();
        }

        File file = new File(coverPath + "/" + sub + "/" + sha);

        if(file.exists()) {
           return file.getAbsolutePath();
        }

        byte[] imageBytes = MetaDataExtractor.getEmbeddedPicture(TrackPath);

        if (imageBytes == null || imageBytes.length == 0) {
            // is this an intended side effect or should it throw an error?
            return null;
        }

        if(sizeLimit > 0  && width > 0 && imageBytes.length > sizeLimit) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                int height = (width * bitmap.getHeight()) / (bitmap.getWidth() > 0 ? bitmap.getWidth() : 1);
                if (width < bitmap.getWidth()) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                }
                ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, compressedStream)) {
                    if (compressedStream.size() > 0 && compressedStream.size() < imageBytes.length) {
                        imageBytes = compressedStream.toByteArray();
                    }
                }
            } catch (Exception e) {
                // why supress the Exception?
                Log.d("RNGMF", "Failed to compress image", e);
            }
        }

        if (imageBytes == null || imageBytes.length == 0) {
            return null;
        }

        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(imageBytes);
            fos.flush();
        }
        return file.getAbsolutePath();
    }

    static class Pair implements Comparable {
        public long t;
        public File f;

        public Pair(File file) {
            f = file;
            t = file.lastModified();
        }

        public int compareTo(Object o) {
            long u = ((Pair) o).t;
            return Long.compare(u, t);
        }
    }

    public static List<String> listFilesInFolders(ArrayList<File> folders, int minFileSize, int maxFileSize, String extensionFilter, boolean sorted) {
        List<String> filesPaths = new ArrayList<>();
        for (final File folder: folders) {
            addFilesInFolder(folder, minFileSize, maxFileSize, extensionFilter, filesPaths);
        }
        if(sorted){
            int listSize = filesPaths.size();
            Pair[] pairs = new Pair[listSize];
            for (int i = 0; i < listSize; i++)
                pairs[i] = new Pair(new File(filesPaths.get(i)));
            Arrays.sort(pairs);
            for (int i = 0; i < listSize; i++)
                filesPaths.set(i,pairs[i].f.getAbsolutePath());
        }
        return filesPaths;
    }

    private static void addFilesInFolder(File folder, int minFileSize, int maxFileSize, String extensionFilter, List<String> list) {
        if (! folder.isDirectory()) {
            return;
        }
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                addFilesInFolder(fileEntry, minFileSize, maxFileSize, extensionFilter, list);
            } else {
                long fileSize = fileEntry.length();
                if (fileSize < maxFileSize && fileSize > minFileSize && fileEntry.toString().toLowerCase().endsWith(extensionFilter)) {
                    list.add(fileEntry.getAbsolutePath());
                }
            }
        }
    }
}
