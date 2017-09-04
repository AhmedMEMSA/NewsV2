package memsa.ahmed.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import memsa.ahmed.news.Activities.NewsDetailsActivity;
import memsa.ahmed.news.Pojo.Article;
import memsa.ahmed.news.R;
import memsa.ahmed.news.Utils.Convert;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    public static int TYPE_MAIN = 0;
    public static int TYPE_DEFAULT = 1;
    private Context context;
    private List<Article> items;
    private int viewFlag;


    public ArticlesAdapter(int viewType) {
        this.viewFlag = viewType;
        items = new ArrayList<>();
    }

    public List<Article> getItems() {
        return items;
    }

    public void setItems(List<Article> items) {
        this.items = items;
    }

    public void delete(int po) {
        items.remove(po);
        this.notifyDataSetChanged();
    }

    public void addItem(Article item) {
        items.add(item);
        this.notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v;
        if (viewFlag == TYPE_MAIN) {
            v = LayoutInflater.from(context).inflate(R.layout.item_rv_main_news, parent, false);
        } else if (viewFlag == TYPE_DEFAULT) {
            v = LayoutInflater.from(context).inflate(R.layout.item_rv_news, parent, false);
        } else {
            throw new RuntimeException(context.toString()
                    + context.getString(R.string.error_viewtype));
        }
        ArticleViewHolder holder = new ArticleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {
        final Article article = items.get(position);
        if (article.getUrlToImage() != null)
            Picasso.with(context).load(article.getUrlToImage()).into(holder.iv_ArticleImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.iv_ArticleImage.setContentDescription(context.getString(R.string.image_to).concat(article.getTitle()));

                }

                @Override
                public void onError() {
                    holder.iv_ArticleImage.setBackgroundResource(R.drawable.ic_news_logo_white);
                    holder.iv_ArticleImage.getLayoutParams().width = (int) Convert.convertDpToPixel(250, context);
                    holder.iv_ArticleImage.setContentDescription(context.getString(R.string.default_image));

                }
            });


        holder.tv_ArticleTitle.setText(article.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.iv_item_article_image)
        ImageView iv_ArticleImage;
        @BindView(R.id.tv_item_article_title)
        TextView tv_ArticleTitle;
        @BindView(R.id.rl_item_parent)
        RelativeLayout rl_ArticleParent;

        ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rl_ArticleParent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Article article = items.get(getAdapterPosition());
//            Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra(NewsDetailsActivity.EXTRA_ARTICLE, article);
            context.startActivity(intent);
        }

    }
}
