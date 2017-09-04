package memsa.ahmed.news.Sync;

import android.content.ContentResolver;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import memsa.ahmed.news.Pojo.Article;
import memsa.ahmed.news.Provider.NewsContract;
import memsa.ahmed.news.Utils.Preferences;
import memsa.ahmed.news.Utils.Urls;

/**
 * Created by asmaa on 8/29/2017.
 */

public class NewsSyncTask {


    synchronized public static void syncNewsOne(final Context context) {
        System.out.println("Sync : task one called");
        final String id = new Preferences(context).getFavoriteSourceOne().getId();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, Urls.getARTICLES(id)
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("articles");
                            Gson gson = new Gson();
                            ContentResolver resolver = context.getContentResolver();
                            String selection = NewsContract.ArticleEntry.COLUMN_SOURCE_ID + "=?";
                            resolver.delete(NewsContract.ArticleEntry.CONTENT_URI, selection, new String[]{id});

                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    Article article = gson.fromJson(object.toString(), Article.class);
                                    resolver.insert(NewsContract.ArticleEntry.CONTENT_URI, article.toContentValues(id));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        FirebaseCrash.report(error);
                    }
                }
        );
        queue.add(request);
    }

    synchronized public static void syncNewsTwo(final Context context) {
        System.out.println("Sync : task two called");

        final String id = new Preferences(context).getFavoriteSourceTwo().getId();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, Urls.getARTICLES(id)
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("articles");
                            Gson gson = new Gson();
                            ContentResolver resolver = context.getContentResolver();
                            String selection = NewsContract.ArticleEntry.COLUMN_SOURCE_ID + "=?";
                            resolver.delete(NewsContract.ArticleEntry.CONTENT_URI, selection, new String[]{id});

                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    Article article = gson.fromJson(object.toString(), Article.class);
                                    resolver.insert(NewsContract.ArticleEntry.CONTENT_URI, article.toContentValues(id));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        FirebaseCrash.report(error);
                    }
                }
        );
        queue.add(request);
    }

    synchronized public static void syncNewsThree(final Context context) {
        System.out.println("Sync : task three called");

        final String id = new Preferences(context).getFavoriteSourceThree().getId();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, Urls.getARTICLES(id)
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("articles");
                            Gson gson = new Gson();
                            ContentResolver resolver = context.getContentResolver();
                            String selection = NewsContract.ArticleEntry.COLUMN_SOURCE_ID + "=?";
                            resolver.delete(NewsContract.ArticleEntry.CONTENT_URI, selection, new String[]{id});

                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    Article article = gson.fromJson(object.toString(), Article.class);
                                    resolver.insert(NewsContract.ArticleEntry.CONTENT_URI, article.toContentValues(id));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        FirebaseCrash.report(error);
                    }
                }
        );
        queue.add(request);
    }
}
