package memsa.ahmed.news.Pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import memsa.ahmed.news.Provider.NewsContract;

/**
 * Created by asmaa on 8/14/2017.
 */

public class Article implements Parcelable {
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Article() {
    }


    public Article(Cursor cursor) {
        this.author = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleEntry.COLUMN_AUTHOR));
        this.title = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleEntry.COLUMN_TITLE));
        this.description = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleEntry.COLUMN_DESCRIPTION));
        this.url = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleEntry.COLUMN_URL));
        this.urlToImage = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleEntry.COLUMN_URL_TO_IMAGE));
        this.publishedAt = cursor.getString(cursor.getColumnIndex(NewsContract.ArticleEntry.COLUMN_PUBLISHED_AT));
    }

    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

    public ContentValues toContentValues(String sourceId) {
        ContentValues values = new ContentValues();
        values.put(NewsContract.ArticleEntry.COLUMN_AUTHOR, getAuthor());
        values.put(NewsContract.ArticleEntry.COLUMN_DESCRIPTION, getDescription());
        values.put(NewsContract.ArticleEntry.COLUMN_TITLE, getTitle());
        values.put(NewsContract.ArticleEntry.COLUMN_PUBLISHED_AT, getPublishedAt());
        values.put(NewsContract.ArticleEntry.COLUMN_URL_TO_IMAGE, getUrlToImage());
        values.put(NewsContract.ArticleEntry.COLUMN_URL, getUrl());
        values.put(NewsContract.ArticleEntry.COLUMN_SOURCE_ID, sourceId);
        return values;
    }
}
