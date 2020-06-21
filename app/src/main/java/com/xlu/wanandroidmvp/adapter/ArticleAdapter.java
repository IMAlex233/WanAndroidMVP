package com.xlu.wanandroidmvp.adapter;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.common.Application;
import com.xlu.wanandroidmvp.http.bean.Article;
import com.xlu.wanandroidmvp.utils.JUtils;
import com.xlu.wanandroidmvp.utils.StringUtils;

import java.util.List;

import timber.log.Timber;

public class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public static final int TYPE_COMMON = 1;
    public static final int TYPE_COLLECTION = 2;

    private int mType;
    private RequestOptions options = new RequestOptions().error(Application.getInstance().getDrawable(R.color.red))
                                                         .transform(new RoundedCorners(20));
    private LikeListener likeListener;


    public void setLikeListener(LikeListener likeListener) {
        this.likeListener = likeListener;
    }

    public interface LikeListener {

        void liked(Article item, int adapterPosition);

        void unLiked(Article item, int adapterPosition);
    }

    public ArticleAdapter(List<Article> articles, int type) {
        super(R.layout.item_article, articles);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.tvAuthor, StringUtils.isEmpty(item.getAuthor()) ? StringUtils.isEmpty(item.getShareUser()) ? "匿名" : item.getShareUser() : item.getAuthor())
              .setText(R.id.tvDate, item.getNiceDate())
              .setText(R.id.tvTitle, Html.fromHtml(item.getTitle()).toString())
              .setText(R.id.tvDesc, Html.fromHtml(item.getDesc()).toString())
              .setGone(R.id.tvTagTop, item.isTop())
              .setGone(R.id.tvTagNew, item.isFresh())
              .setGone(R.id.tvTagQa, StringUtils.equals(item.getSuperChapterName(), "问答"))
              .setGone(R.id.tvDesc, !StringUtils.isEmpty(item.getDesc()))
              .setGone(R.id.ivProject, !StringUtils.isEmpty(item.getEnvelopePic())) //image
              .addOnClickListener(R.id.tvAuthor, R.id.tvType);

        LikeButton ivLike = helper.itemView.findViewById(R.id.ivLike);
        ivLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (likeListener != null) {
                    likeListener.liked(item, helper.getAdapterPosition());
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (likeListener != null) {
                    likeListener.unLiked(item, helper.getAdapterPosition());
                }
            }
        });

        switch (mType) {
            case TYPE_COLLECTION:
                ivLike.setLiked(true);
                helper.setText(R.id.tvType, item.getChapterName());
                break;
            case TYPE_COMMON:
            default:
                ivLike.setLiked(item.isCollect());
                helper.setText(R.id.tvType, String.format("%s/%s", item.getSuperChapterName(), item.getChapterName()));
                break;
        }

        ImageView ivProject = helper.itemView.findViewById(R.id.ivProject);
        if (!StringUtils.isEmpty(item.getEnvelopePic())) {
            Glide.with(mContext).load(item.getEnvelopePic()).apply(options).into(ivProject);
        }
    }


    public void restoreLike(int position) {
        LikeButton likeButton = (LikeButton)getViewByPosition(position, R.id.ivLike);
        if (likeButton == null) {
            Timber.e("没找到按钮");
            return;
        }
        likeButton.setLiked(!likeButton.isLiked());
        notifyItemChanged(position);
    }
}
