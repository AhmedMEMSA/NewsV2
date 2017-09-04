package memsa.ahmed.news.Dialogs;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import memsa.ahmed.news.Activities.MainActivity;
import memsa.ahmed.news.Activities.SplashActivity;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.Provider.NewsContract;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Sync.NewsSyncUtils;
import memsa.ahmed.news.Utils.Handler;
import memsa.ahmed.news.Utils.Preferences;
import memsa.ahmed.news.Utils.Urls;


/**
 * Created by ahmed on 8/2/2016.
 */
public class EditFavoriteDialog extends Dialog {
    public static final int FLAG_SOURCE1 = 1;
    public static final int FLAG_SOURCE2 = 2;
    public static final int FLAG_SOURCE3 = 3;
    Context context;
    int flag = 0;
    @BindView(R.id.spinner_source_edit)
    Spinner spinner;
    @BindView(R.id.favorite_source_edit_error)
    TextView errorTV;
    @BindView(R.id.favorite_source_edit_progress)
    ProgressBar progressBar;
    private Preferences preferences;
    private Source oldSource;

    public EditFavoriteDialog(Context context, int flag) {
        super(context);
        this.context = context;
        preferences = new Preferences(context);
        this.flag = flag;
        if (flag == FLAG_SOURCE1) {
            oldSource = preferences.getFavoriteSourceOne();
        } else if (flag == FLAG_SOURCE2) {
            oldSource = preferences.getFavoriteSourceTwo();
        } else if (flag == FLAG_SOURCE3) {
            oldSource = preferences.getFavoriteSourceThree();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(true);
        setContentView(R.layout.dialog_edit_favorite);
        ButterKnife.bind(this);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getSources();

        findViewById(R.id.dialog_edit_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        findViewById(R.id.dialog_edit_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Source) spinner.getSelectedItem()).getId().equals(oldSource.getId())) {
                    ContentResolver resolver = context.getContentResolver();
                    String selection = NewsContract.ArticleEntry.COLUMN_SOURCE_ID + "=?";
                    resolver.delete(NewsContract.ArticleEntry.CONTENT_URI, selection, new String[]{oldSource.getId()});
                    if (flag == FLAG_SOURCE1) {
                        preferences.saveFavoriteSourcesOne((Source) spinner.getSelectedItem());
                    } else if (flag == FLAG_SOURCE2) {
                        preferences.saveFavoriteSourcesTwo((Source) spinner.getSelectedItem());
                    } else if (flag == FLAG_SOURCE3) {
                        preferences.saveFavoriteSourcesThree((Source) spinner.getSelectedItem());
                    }
                    NewsSyncUtils.initialize(context);
                    context.startActivity(new Intent(context, SplashActivity.class));
                    ((MainActivity) context).finish();
                    dismiss();
                } else {
                    cancel();
                }

            }
        });

    }

    private void getSources() {
        progressBar.setVisibility(View.VISIBLE);
        errorTV.setVisibility(View.INVISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, Urls.getSOURCES()
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("sources");
                            Gson gson = new Gson();
                            ArrayList<Source> sources = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    Source s = gson.fromJson(object.toString(), Source.class);
                                    sources.add(s);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, sources); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(spinnerArrayAdapter);
                            for (int i = 0; i < spinnerArrayAdapter.getCount(); i++) {
                                if (((Source) spinnerArrayAdapter.getItem(i)).getId().equals(oldSource.getId())) {
                                    spinner.setSelection(i);
                                }
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            errorTV.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        errorTV.setVisibility(View.VISIBLE);
                        errorTV.setText(Handler.volleyErrorHandler(context, error));
                        FirebaseCrash.report(error);
                    }
                }
        );
        queue.add(request);
    }

}
