package memsa.ahmed.news.Utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import memsa.ahmed.news.R;

/**
 * Created by Ahmed Magdy on 4/12/2017.
 */

public class Handler {

    public static String volleyErrorHandler(Context context, VolleyError error) {
        if (error instanceof TimeoutError) {
            return (context.getString(R.string.error_timeout));
        } else if (error instanceof NoConnectionError) {
            return (context.getString(R.string.error_connection));
        } else if (error instanceof AuthFailureError) {
            return (context.getString(R.string.error_auth));
        } else if (error instanceof ServerError) {
            return (context.getString(R.string.error_server));
        } else if (error instanceof NetworkError) {
            return (context.getString(R.string.error_network));
        } else if (error instanceof ParseError) {
            return (context.getString(R.string.error_parse));
        } else {
            return (context.getString(R.string.error_unknown));
        }
    }
}
