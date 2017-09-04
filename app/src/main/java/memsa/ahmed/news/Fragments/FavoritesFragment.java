package memsa.ahmed.news.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import memsa.ahmed.news.Adapters.ArticlesAdapter;
import memsa.ahmed.news.Dialogs.EditFavoriteDialog;
import memsa.ahmed.news.Pojo.Article;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.Provider.NewsContract;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int ID_LOADER = 100;
    ArrayList<Source> favoriteSources;
    @BindView(R.id.tv_title_source_one)
    TextView tv_TitleSourceOne;
    @BindView(R.id.tv_title_source_two)
    TextView tv_TitleSourceTwo;
    @BindView(R.id.tv_title_source_three)
    TextView tv_TitleSourceThree;
    @BindView(R.id.rv_source_one)
    RecyclerView rv_SourceOne;
    @BindView(R.id.rv_source_two)
    RecyclerView rv_SourceTwo;
    @BindView(R.id.rv_source_three)
    RecyclerView rv_SourceThree;
    @BindView(R.id.progress_bar_source_one)
    ProgressBar pb_SourceOne;
    @BindView(R.id.progress_bar_source_two)
    ProgressBar pb_SourceTwo;
    @BindView(R.id.progress_bar_source_three)
    ProgressBar pb_SourceThree;
    RecyclerView.LayoutManager layoutManagerOne, layoutManagerTwo, layoutManagerThree;
    ArticlesAdapter adapterOne, adapterTwo, adapterThree;
    private Unbinder unbinder;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteSources = new ArrayList<>();
        favoriteSources.addAll(new Preferences(getActivity()).getFavoriteSources());
        System.out.println(favoriteSources);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        System.out.println(favoriteSources.get(0).getName());

        tv_TitleSourceOne.setText(favoriteSources.get(0).getName());
        tv_TitleSourceTwo.setText(favoriteSources.get(1).getName());
        tv_TitleSourceThree.setText(favoriteSources.get(2).getName());

        layoutManagerOne = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerTwo = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerThree = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        adapterOne = new ArticlesAdapter(ArticlesAdapter.TYPE_MAIN);
        adapterTwo = new ArticlesAdapter(ArticlesAdapter.TYPE_MAIN);
        adapterThree = new ArticlesAdapter(ArticlesAdapter.TYPE_MAIN);

        rv_SourceOne.setAdapter(adapterOne);
        rv_SourceTwo.setAdapter(adapterTwo);
        rv_SourceThree.setAdapter(adapterThree);

        rv_SourceOne.setLayoutManager(layoutManagerOne);
        rv_SourceTwo.setLayoutManager(layoutManagerTwo);
        rv_SourceThree.setLayoutManager(layoutManagerThree);

        getActivity().getSupportLoaderManager().initLoader(ID_LOADER, null, this);


        rootView.findViewById(R.id.favorite_source_one_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditFavoriteDialog(getActivity(), EditFavoriteDialog.FLAG_SOURCE1).show();
            }
        });
        rootView.findViewById(R.id.favorite_source_two_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditFavoriteDialog(getActivity(), EditFavoriteDialog.FLAG_SOURCE2).show();
            }
        });
        rootView.findViewById(R.id.favorite_source_three_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditFavoriteDialog(getActivity(), EditFavoriteDialog.FLAG_SOURCE3).show();
            }
        });

        return rootView;
    }

    private void showProgress() {
        pb_SourceOne.setVisibility(View.VISIBLE);
        pb_SourceTwo.setVisibility(View.VISIBLE);
        pb_SourceThree.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pb_SourceOne.setVisibility(View.INVISIBLE);
        pb_SourceTwo.setVisibility(View.INVISIBLE);
        pb_SourceThree.setVisibility(View.INVISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_LOADER:
                showProgress();
                return new CursorLoader(getActivity(),
                        NewsContract.ArticleEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        hideProgress();
        List<Article> articlesSourceOne = new ArrayList<>();
        List<Article> articlesSourceTwo = new ArrayList<>();
        List<Article> articlesSourceThree = new ArrayList<>();
        Preferences preferences = new Preferences(getActivity());
        while (data.moveToNext()) {
            String sourceId = data.getString(data.getColumnIndex(NewsContract.ArticleEntry.COLUMN_SOURCE_ID));
            System.out.println(sourceId);
            if (sourceId.equals(preferences.getFavoriteSourceOne().getId())) {
                articlesSourceOne.add(new Article(data));
            } else if (sourceId.equals(preferences.getFavoriteSourceTwo().getId())) {
                articlesSourceTwo.add(new Article(data));
            } else if (sourceId.equals(preferences.getFavoriteSourceThree().getId())) {
                articlesSourceThree.add(new Article(data));
            }
        }
        adapterOne.setItems(articlesSourceOne);
        adapterOne.notifyDataSetChanged();
        adapterTwo.setItems(articlesSourceTwo);
        adapterTwo.notifyDataSetChanged();
        adapterThree.setItems(articlesSourceThree);
        adapterThree.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getSupportLoaderManager().destroyLoader(ID_LOADER);
        unbinder.unbind();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapterOne.setItems(new ArrayList<Article>());
        adapterOne.notifyDataSetChanged();
        adapterTwo.setItems(new ArrayList<Article>());
        adapterTwo.notifyDataSetChanged();
        adapterThree.setItems(new ArrayList<Article>());
        adapterThree.notifyDataSetChanged();
    }
}
