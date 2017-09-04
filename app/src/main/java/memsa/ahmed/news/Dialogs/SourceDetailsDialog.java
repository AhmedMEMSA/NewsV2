package memsa.ahmed.news.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.R;


/**
 * Created by ahmed on 8/2/2016.
 */
public class SourceDetailsDialog extends Dialog {
    Context context;
    Source source;
    @BindView(R.id.tv_dialog_source_name)
    TextView name;
    @BindView(R.id.tv_dialog_source_description)
    TextView description;
    @BindView(R.id.tv_dialog_source_language)
    TextView language;
    @BindView(R.id.tv_dialog_source_country)
    TextView country;
    @BindView(R.id.tv_dialog_source_category)
    TextView category;
    @BindView(R.id.ll_dialog_source_browser)
    LinearLayout browse;
    @BindView(R.id.ll_dialog_source_share)
    LinearLayout share;
    private FirebaseAnalytics mFirebaseAnalytics;

    public SourceDetailsDialog(Context context, Source source) {
        super(context);
        this.context = context;
        this.source = source;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(true);
        setContentView(R.layout.dialog_source_details);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        name.setText(source.getName());
        category.setText(source.getCategory());
        description.setText(source.getDescription());
        language.append(source.getLanguage());
        country.append(source.getCountry());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, source.getName());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, source.getName() + "\n" + source.getDescription() + "\n url: " + source.getUrl());
                context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SourceDetailsDialog.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "icon source dialog share clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(source.getUrl()));
                context.startActivity(browserIntent);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SourceDetailsDialog.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "icon source dialog browse clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });

    }

}
