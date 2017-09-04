package memsa.ahmed.news.Utils;

import android.net.Uri;

/**
 * Created by asmaa on 8/15/2017.
 */

public class Urls {

    private static String PARAM_APIKEY = "apiKey";
    private static String PARAM_SOURCE = "source";

    private static String VALUE_APIKEY = "a61b03551f764a0883536c193e7cb4b4";

    private static String BASE = "https://newsapi.org";
    private static String SERVICE_BASE = BASE + "/v1";

    private static String SOURCES = SERVICE_BASE + "/sources";
    private static String ARTICLES = SERVICE_BASE + "/articles";

    public static String getSOURCES(String language) {
        String PARAM_LANGUAGE = "language";
        Uri builtUri = Uri.parse(SOURCES)
                .buildUpon()
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();
        return builtUri.toString();
    }

    public static String getSOURCES() {
        Uri builtUri = Uri.parse(SOURCES)
                .buildUpon()
                .build();
        return builtUri.toString();
    }


    public static String getARTICLES(String source) {
        Uri builtUri = Uri.parse(ARTICLES)
                .buildUpon()
                .appendQueryParameter(PARAM_APIKEY, VALUE_APIKEY)
                .appendQueryParameter(PARAM_SOURCE, source)
                .build();
        return builtUri.toString();
    }

    public static String getARTICLES(String source, String sortBy) {
        String PARAM_SORTBY = "sortBy";
        Uri builtUri = Uri.parse(ARTICLES)
                .buildUpon()
                .appendQueryParameter(PARAM_APIKEY, VALUE_APIKEY)
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORTBY, sortBy)
                .build();
        return builtUri.toString();
    }


}
