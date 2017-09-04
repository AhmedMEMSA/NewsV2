package memsa.ahmed.news.Provider;

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
 * Created by eltamayoz04 on 27/08/2017.
 */

public class NewsProvider extends ContentProvider {
    static final int NEWS = 1;
    static final int NEWS_ID = 2;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private NewsDBHelper openHelper;

    public static UriMatcher buildUriMatcher() {
        String content = NewsContract.CONTENT_AUTHORITY;
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, NewsContract.PATH_NEWS, NEWS);
        matcher.addURI(content, NewsContract.PATH_NEWS + "/#", NEWS_ID);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        openHelper = new NewsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NEWS:
                return NewsContract.ArticleEntry.CONTENT_TYPE;
            case NEWS_ID:
                return NewsContract.ArticleEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                cursor = db.query(
                        NewsContract.ArticleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case NEWS_ID:
                long _id = ContentUris.parseId(uri);
                cursor = db.query(
                        NewsContract.ArticleEntry.TABLE_NAME,
                        projection,
                        NewsContract.ArticleEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        long _id;
        Uri returnUri;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                _id = db.insert(NewsContract.ArticleEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = NewsContract.ArticleEntry.buildMovieUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                count = db.delete(NewsContract.ArticleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || count != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                count = db.update(NewsContract.ArticleEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (count != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }
}
