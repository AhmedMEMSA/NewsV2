package memsa.ahmed.news.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import memsa.ahmed.news.Activities.NewsDetailsActivity;
import memsa.ahmed.news.Pojo.Article;
import memsa.ahmed.news.Provider.NewsContract;
import memsa.ahmed.news.R;

public class NewsViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Article> items = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;

    public NewsViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


    }

    @Override
    public void onCreate() {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(NewsContract.ArticleEntry.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            items.add(new Article(cursor));
        }

        cursor.close();

        if (items.size() < 1) {
            Toast.makeText(context, R.string.run_app_first, Toast.LENGTH_LONG).show();
        }
        // no-op
    }

    @Override
    public void onDestroy() {
        items.clear();
    }

    @Override
    public int getCount() {
        return (items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(),
                R.layout.item);

        row.setTextViewText(android.R.id.text1, items.get(position).getTitle());

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putParcelable(NewsDetailsActivity.EXTRA_ARTICLE, items.get(position));
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {


    }
}