package com.silho.ideo.popmoov.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Samuel on 18/02/2018.
 */

public class MovieAsFavProvider extends ContentProvider {

    private MovieAsFavHelper mHelper;
    public static final int MOVIES = 100;
    public static final int MOVIES_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        mHelper = new MovieAsFavHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                cursor = database.query(MovieAsFavContract.MovieAsFavEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIES_ID:
                selection = MovieAsFavContract.MovieAsFavEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(MovieAsFavContract.MovieAsFavEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                return insertMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported" + uri);
        }
    }

    private Uri insertMovie(Uri uri, ContentValues contentValues) {
        String title = contentValues.getAsString(MovieAsFavContract.MovieAsFavEntry.COLUMN_TITLE);
        if(title == null) throw new IllegalArgumentException("Title required");
        String idMovie = contentValues.getAsString(MovieAsFavContract.MovieAsFavEntry.COLUMN_ID);
        if(idMovie == null) throw new IllegalArgumentException("id required");
        String path = contentValues.getAsString(MovieAsFavContract.MovieAsFavEntry.COLUMN_PATH_POSTER);
        if(path == null) throw new IllegalArgumentException("path required");

        SQLiteDatabase database = mHelper.getWritableDatabase();

        long id = database.insert(MovieAsFavContract.MovieAsFavEntry.TABLE_NAME, null, contentValues);
        if(id == -1)
            return null;
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case MOVIES:
                getContext().getContentResolver().notifyChange(uri, null);
                return database.delete(MovieAsFavContract.MovieAsFavEntry.TABLE_NAME, selection, selectionArgs);
            case MOVIES_ID:
                selection = MovieAsFavContract.MovieAsFavEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(MovieAsFavContract.MovieAsFavEntry.TABLE_NAME, selection, selectionArgs);
                if(rowsDeleted != 0) getContext().getContentResolver().notifyChange(uri, null);
                getContext().getContentResolver().notifyChange(uri, null);
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported fot" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                return updateMovie(uri, contentValues, selection, selectionArgs);
            case MOVIES_ID:
                selection = MovieAsFavContract.MovieAsFavEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateMovie(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateMovie(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if(contentValues.containsKey(MovieAsFavContract.MovieAsFavEntry.COLUMN_TITLE)){
            String title = contentValues.getAsString(MovieAsFavContract.MovieAsFavEntry.COLUMN_TITLE);
            if(title == null) throw new IllegalArgumentException("Title required");
        }

        if(contentValues.containsKey(MovieAsFavContract.MovieAsFavEntry.COLUMN_PATH_POSTER)){
            String path = contentValues.getAsString(MovieAsFavContract.MovieAsFavEntry.COLUMN_PATH_POSTER);
            if(path == null) throw new IllegalArgumentException("path required");
        }

        if(contentValues.containsKey(MovieAsFavContract.MovieAsFavEntry.COLUMN_ID)){
            String movieId = contentValues.getAsString(MovieAsFavContract.MovieAsFavEntry.COLUMN_ID);
            if(movieId == null) throw new IllegalArgumentException("id required");
        }

        if(contentValues.size() == 0)return 0;

        SQLiteDatabase database = mHelper.getWritableDatabase();

        int rowsUpdated = database.update(MovieAsFavContract.MovieAsFavEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if(rowsUpdated != 0) getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    static {

        sUriMatcher.addURI(MovieAsFavContract.MovieAsFavEntry.CONTENT_AUTHORITY,
                MovieAsFavContract.MovieAsFavEntry.PATH_MOVIES, MOVIES);

        sUriMatcher.addURI(MovieAsFavContract.MovieAsFavEntry.CONTENT_AUTHORITY,
                MovieAsFavContract.MovieAsFavEntry.PATH_MOVIES + "/#",
                MOVIES_ID);

    }
}
