package memsa.ahmed.news.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eltamayoz04 on 27/08/2017.
 */

public class NewsDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";

    private static final String CREATE_DB_TABLE = "create table " + NewsContract.ArticleEntry.TABLE_NAME
            + " (" + NewsContract.ArticleEntry._ID + " integer primary key autoincrement, "
            + NewsContract.ArticleEntry.COLUMN_SOURCE_ID + " text not null, "
            + NewsContract.ArticleEntry.COLUMN_AUTHOR + " text, "
            + NewsContract.ArticleEntry.COLUMN_TITLE + " text, "
            + NewsContract.ArticleEntry.COLUMN_DESCRIPTION + " text, "
            + NewsContract.ArticleEntry.COLUMN_URL + " text, "
            + NewsContract.ArticleEntry.COLUMN_URL_TO_IMAGE + " text, "
            + NewsContract.ArticleEntry.COLUMN_PUBLISHED_AT + " text);";

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.ArticleEntry.TABLE_NAME);
        onCreate(db);
    }
}
