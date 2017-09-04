package memsa.ahmed.news.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;

import memsa.ahmed.news.Pojo.Source;

/**
 * Created by Ahmed Magdy on 4/29/2017.
 */

public class Preferences {


    private static final String FIRST_RUN = "firstRun";
    private static final String FAVORITE_ONE = "favoriteSourceOne";
    private static final String FAVORITE_TWO = "favoriteSourceTwo";
    private static final String FAVORITE_THREE = "favoriteSourceThree";

    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public ArrayList<Source> getFavoriteSources() {
        ArrayList<Source> sources = new ArrayList<>();
        Gson gson = new Gson();
        if (!sharedPreferences.getString(FAVORITE_ONE, "").equals(""))
            sources.add(gson.fromJson(sharedPreferences.getString(FAVORITE_ONE, ""), Source.class));
        if (!sharedPreferences.getString(FAVORITE_TWO, "").equals(""))
            sources.add(gson.fromJson(sharedPreferences.getString(FAVORITE_TWO, ""), Source.class));
        if (!sharedPreferences.getString(FAVORITE_THREE, "").equals(""))
            sources.add(gson.fromJson(sharedPreferences.getString(FAVORITE_THREE, ""), Source.class));
        return sources;
    }

    public Source getFavoriteSourceOne() {
        Gson gson = new Gson();
        String s = sharedPreferences.getString(FAVORITE_ONE, "");
        if (!s.isEmpty()) {
            return (gson.fromJson(s, Source.class));
        } else {
            return null;
        }
    }

    public Source getFavoriteSourceTwo() {
        Gson gson = new Gson();
        String s = sharedPreferences.getString(FAVORITE_TWO, "");
        if (!s.isEmpty()) {
            return (gson.fromJson(s, Source.class));
        } else {
            return null;
        }
    }

    public Source getFavoriteSourceThree() {
        Gson gson = new Gson();
        String s = sharedPreferences.getString(FAVORITE_THREE, "");
        if (!s.isEmpty()) {
            return (gson.fromJson(s, Source.class));
        } else {
            return null;
        }
    }

    public void clearFavoriteSources() {
        sharedPreferences.edit().putString(FAVORITE_ONE, "").apply();
        sharedPreferences.edit().putString(FAVORITE_TWO, "").apply();
        sharedPreferences.edit().putString(FAVORITE_THREE, "").apply();
    }

    public void saveFavoriteSources(Source favoriteSourceOne, Source favoriteSourceTwo, Source favoriteSourceThree) {
        Gson gson = new Gson();
        sharedPreferences.edit().putString(FAVORITE_ONE, gson.toJson(favoriteSourceOne)).apply();
        sharedPreferences.edit().putString(FAVORITE_TWO, gson.toJson(favoriteSourceTwo)).apply();
        sharedPreferences.edit().putString(FAVORITE_THREE, gson.toJson(favoriteSourceThree)).apply();
    }

    public void saveFavoriteSourcesOne(Source favoriteSourceOne) {
        Gson gson = new Gson();
        sharedPreferences.edit().putString(FAVORITE_ONE, gson.toJson(favoriteSourceOne)).apply();
    }

    public void saveFavoriteSourcesTwo(Source favoriteSourceTwo) {
        Gson gson = new Gson();
        sharedPreferences.edit().putString(FAVORITE_TWO, gson.toJson(favoriteSourceTwo)).apply();
    }

    public void saveFavoriteSourcesThree(Source favoriteSourceThree) {
        Gson gson = new Gson();
        sharedPreferences.edit().putString(FAVORITE_THREE, gson.toJson(favoriteSourceThree)).apply();
    }


    public void clearFirstRun() {
        sharedPreferences.edit().putBoolean(FIRST_RUN, false).apply();
    }

    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(FIRST_RUN, true);
    }

}
