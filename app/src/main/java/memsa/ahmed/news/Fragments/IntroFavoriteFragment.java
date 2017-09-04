package memsa.ahmed.news.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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
import memsa.ahmed.news.Activities.IntroActivity;
import memsa.ahmed.news.Activities.SplashActivity;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Handler;
import memsa.ahmed.news.Utils.Preferences;
import memsa.ahmed.news.Utils.Urls;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFavoriteFragment extends Fragment {

    @BindView(R.id.favorite_source_one_progress)
    ProgressBar progressOne;
    @BindView(R.id.favorite_source_two_progress)
    ProgressBar progressTwo;
    @BindView(R.id.favorite_source_three_progress)
    ProgressBar progressThree;
    @BindView(R.id.spinner_source_one)
    Spinner spinnerOne;
    @BindView(R.id.spinner_source_two)
    Spinner spinnerTwo;
    @BindView(R.id.spinner_source_three)
    Spinner spinnerThree;
    private Unbinder unbinder;
    private ArrayList<Source> sources;
    private ArrayAdapter<Source> spinnerArrayAdapter;
    private Preferences preferences;
    private boolean loading;
    private Snackbar snackbar;

    public IntroFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_favorite, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        preferences = new Preferences(getActivity());
        getSources();

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }

    private void getSources() {
        showProgress();
        loading = true;
        sources = new ArrayList<>();
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
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    Source s = gson.fromJson(object.toString(), Source.class);
                                    sources.add(s);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            loading = false;
                            ((IntroActivity) getActivity()).fabnext.setEnabled(true);


                            hideProgress();
                            setSpinnersData();

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

    private void setSpinnersData() {
        spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sources); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOne.setAdapter(spinnerArrayAdapter);
        spinnerTwo.setAdapter(spinnerArrayAdapter);
        spinnerThree.setAdapter(spinnerArrayAdapter);
    }

    private void hideProgress() {
        progressOne.setVisibility(View.GONE);
        progressTwo.setVisibility(View.GONE);
        progressThree.setVisibility(View.GONE);
    }

    private void showProgress() {
        progressOne.setVisibility(View.VISIBLE);
        progressTwo.setVisibility(View.VISIBLE);
        progressThree.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void done() {
        if (loading)
            return;
        Source fav1 = (Source) spinnerOne.getSelectedItem();
        Source fav2 = (Source) spinnerTwo.getSelectedItem();
        Source fav3 = (Source) spinnerThree.getSelectedItem();

        if (fav1.getId().equals(fav2.getId()) || fav2.getId().equals(fav3.getId()) || fav1.getId().equals(fav3.getId())) {
            Toast.makeText((getActivity()), R.string.select_different_sources, Toast.LENGTH_SHORT).show();
            return;
        }

        preferences.saveFavoriteSourcesOne(fav1);
        preferences.saveFavoriteSourcesTwo(fav2);
        preferences.saveFavoriteSourcesThree(fav3);

        startActivity(new Intent(getActivity(), SplashActivity.class));
        getActivity().finish();
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
