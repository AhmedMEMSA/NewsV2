package memsa.ahmed.news.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import butterknife.Unbinder;
import memsa.ahmed.news.Adapters.SourcesAdapter;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Handler;
import memsa.ahmed.news.Utils.Urls;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourcesFragment extends Fragment {

    @BindView(R.id.rv_sources)
    RecyclerView rv_Sources;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private Unbinder unbinder;
    private RecyclerView.LayoutManager layoutManagerOne;
    private SourcesAdapter adapter;
    private Snackbar snackbar;

    public SourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sources, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        layoutManagerOne = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new SourcesAdapter();
        rv_Sources.setAdapter(adapter);
        rv_Sources.setLayoutManager(layoutManagerOne);
        getSources();

        return rootView;
    }

    private void getSources() {
        showProgress();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                            adapter.setItems(sources);
                            adapter.notifyDataSetChanged();
                            hideProgress();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgress();
                        showError(Handler.volleyErrorHandler(getActivity(), error));
                        FirebaseCrash.report(error);
                    }
                }
        );
        queue.add(request);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showError(String error) {
        snackbar = Snackbar
                .make(getView(), error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSources();
                    }
                });
        snackbar.show();
    }
}
