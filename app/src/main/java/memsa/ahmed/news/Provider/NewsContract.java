package memsa.ahmed.news.Provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eltamayoz04 on 27/08/2017.
 */

public class NewsContract {

    public static final String CONTENT_AUTHORITY = "memsa.ahmed.news";
    public static final String PATH_NEWS = "news";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ArticleEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_NEWS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_NEWS;

        public static final String TABLE_NAME = "articleTable";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_SOURCE_ID = "sourceID";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "publishedAt";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
