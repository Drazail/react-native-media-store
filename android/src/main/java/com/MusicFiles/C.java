package com.MusicFiles;

import android.provider.MediaStore;

public class C {
    // is used in Options package
    public enum SortBy {
        /**
         * all are used in Options
         * @see com.MusicFiles.Models.Options.BsaeClasses.BaseOptions
         */
        ARTIST(MediaStore.Audio.Media.ARTIST),
        ALBUM(MediaStore.Audio.Media.ALBUM),
        TITLE(MediaStore.Audio.Media.TITLE),
        DATE_MODIFIED(MediaStore.Audio.Media.DATE_MODIFIED),
        DATE_ADDED(MediaStore.Audio.Media.DATE_ADDED);

        private final String sort;

        SortBy(String sort) {
            this.sort = sort;
        }

        public String getValue() {
            return sort;
        }

    }


    public enum SortOrder {

        /**
         *  all are used in Options
         * @see com.MusicFiles.Models.Options.BsaeClasses.BaseOptions
         */

        ASC("ASC"),
        DESC("DESC");

        private final String order;

        SortOrder(String order) {
            this.order = order;
        }

        public String getValue() {
            return order;
        }
    }

    public enum ErrorEnum {
        EMPTY_CURSOR(404, "Either the cursor is null or there are no results for this query");

        private final int code;
        private final String description;

        ErrorEnum(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return code + ": " + description;
        }
    }

}
