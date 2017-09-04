package memsa.ahmed.news.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import memsa.ahmed.news.Adapters.ArticlesAdapter;
import memsa.ahmed.news.Dialogs.SourceDetailsDialog;
import memsa.ahmed.news.Pojo.Article;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Urls;

public class NewsActivity extends AppCompatActivity {

    public static final String EXTRA_SOURCE = "extraSource";

    @BindView(R.id.rv_source_news)
    RecyclerView rv_SourceNews;
    private StaggeredGridLayoutManager layoutManager;
    private ArticlesAdapter adapter;
    private Source source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        source = getIntent().getParcelableExtra(EXTRA_SOURCE);
        getSupportActionBar().setTitle(source.getName());

        if (getResources().getConfiguration().screenWidthDp < 600)
            layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        else if (getResources().getConfiguration().screenWidthDp >= 600)
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ArticlesAdapter(ArticlesAdapter.TYPE_DEFAULT);
        rv_SourceNews.setAdapter(adapter);
        rv_SourceNews.setLayoutManager(layoutManager);
        getSourceNews();
    }

    private void getSourceNews() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Urls.getARTICLES(source.getId())
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Article>>() {
                            }.getType();
                            List<Article> articles = gson.fromJson(jsonObject.getJSONArray("articles").toString(), listType);
                            System.out.println(response);
                            System.out.println(articles);
                            adapter.setItems(articles);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                new SourceDetailsDialog(this, source).show();
                break;
        }
        return true;
    }
}


