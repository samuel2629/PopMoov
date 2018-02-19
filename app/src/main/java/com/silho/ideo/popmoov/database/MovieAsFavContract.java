package com.silho.ideo.popmoov.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Samuel on 18/02/2018.
 */

public class MovieAsFavContract {

    private MovieAsFavContract(){}

    public static final class  MovieAsFavEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.silho.ideo.popmoov";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_MOVIES = "movies";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PATH_POSTER = "poster_path";
        public static final String COLUMN_ID = "id";


    }
}
