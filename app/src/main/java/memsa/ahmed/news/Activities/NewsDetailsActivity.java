package memsa.ahmed.news.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import memsa.ahmed.news.Pojo.Article;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Convert;

public class NewsDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extraNews";
    @BindView(R.id.iv_article_image)
    ImageView iv_ArticleImage;
    @BindView(R.id.tv_article_title)
    TextView tv_ArticleTitle;
    @BindView(R.id.tv_article_date)
    TextView tv_ArticleDate;
    @BindView(R.id.tv_article_description)
    TextView tv_ArticleDescription;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        article = getIntent().getParcelableExtra(EXTRA_ARTICLE);
        System.out.println(article.toString());
        Picasso.with(this).load(article.getUrlToImage()).into(iv_ArticleImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                iv_ArticleImage.setBackgroundResource(R.drawable.ic_news_logo_white);
                iv_ArticleImage.getLayoutParams().width = (int) Convert.convertDpToPixel(300, NewsDetailsActivity.this);
                iv_ArticleImage.getLayoutParams().height = (int) Convert.convertDpToPixel(300, NewsDetailsActivity.this);

            }
        });
        iv_ArticleImage.setContentDescription(getString(R.string.image_to).concat(article.getTitle()));

        tv_ArticleDate.setText(article.getPublishedAt().replace("T", " ").replace("Z", ""));
        tv_ArticleDescription.setText(article.getDescription());
        tv_ArticleTitle.setText(article.getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, article.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, article.getTitle() + "\n" + article.getDescription() + "\n url: " + article.getUrl());
                NewsDetailsActivity.this.startActivity(Intent.createChooser(sharingIntent, NewsDetailsActivity.this.getResources().getString(R.string.share_using)));

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, NewsDetailsActivity.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Fab share article clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });

        findViewById(R.id.ll_open_in_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                NewsDetailsActivity.this.startActivity(browserIntent);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, NewsDetailsActivity.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Button article browser clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });
    }
}
