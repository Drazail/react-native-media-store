package com.MusicFiles.Utils;

import com.MusicFiles.C;

public class OrderByGenerator {
    public static String generateSortOrder(C.SortBy sortBy, C.SortOrder sortOrder) {
        return (sortBy.getValue() + " " + sortOrder.getValue());
    }
}
