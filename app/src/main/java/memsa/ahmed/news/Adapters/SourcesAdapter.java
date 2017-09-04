package memsa.ahmed.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import memsa.ahmed.news.Activities.NewsActivity;
import memsa.ahmed.news.Dialogs.SourceDetailsDialog;
import memsa.ahmed.news.Pojo.Source;
import memsa.ahmed.news.R;


public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.ArticleViewHolder> {

    private Context context;
    private List<Source> items;
    private FirebaseAnalytics mFirebaseAnalytics;


    public SourcesAdapter() {
        items = new ArrayList<>();
    }


    public List<Source> getItems() {
        return items;
    }

    public void setItems(List<Source> items) {
        this.items = items;
    }

    public void delete(int po) {
        items.remove(po);
        this.notifyDataSetChanged();
    }

    public void addItem(Source item) {
        items.add(item);
        this.notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_rv_sources, parent, false);
        ArticleViewHolder holder = new ArticleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.tv_SourceName.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tv_item_source_browser)
        ImageView iv_OpenInBrowser;
        @BindView(R.id.tv_item_source_name)
        TextView tv_SourceName;
        @BindView(R.id.tv_item_source_share)
        ImageView iv_SourceShare;
        @BindView(R.id.tv_item_source_info)
        ImageView iv_SourceInfo;
        @BindView(R.id.ll_item_parent)
        LinearLayout ll_ArticleParent;

        ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            iv_OpenInBrowser.setOnClickListener(this);
            ll_ArticleParent.setOnClickListener(this);
            iv_SourceShare.setOnClickListener(this);
            iv_SourceInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            Source source = items.get(getAdapterPosition());
            if (v.getId() == R.id.ll_item_parent) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra(NewsActivity.EXTRA_SOURCE, source);
                context.startActivity(intent);
            } else if (v.getId() == R.id.tv_item_source_info) {
                new SourceDetailsDialog(context, source).show();
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SourcesAdapter.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Icon source info clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            } else if (v.getId() == R.id.tv_item_source_share) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, source.getName());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, source.getName() + "\n" + source.getDescription() + "\n url: " + source.getUrl());
                context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SourcesAdapter.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Icon source share clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            } else if (v.getId() == R.id.tv_item_source_browser) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(source.getUrl()));
                context.startActivity(browserIntent);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SourcesAdapter.class.getSimpleName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Icon source browser clicked");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }

        }

    }
}
