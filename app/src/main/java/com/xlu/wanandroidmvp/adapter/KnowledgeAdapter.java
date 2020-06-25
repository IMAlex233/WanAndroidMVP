package com.xlu.wanandroidmvp.adapter;

import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.http.bean.Tab;

import java.util.List;


public class KnowledgeAdapter extends BaseQuickAdapter<Tab, BaseViewHolder> {

    private OnChildClickListener mOnChildClickListener;

    public KnowledgeAdapter(int layoutResId, @Nullable List<Tab> data) {
        super(layoutResId, data);
    }

    public void setmOnChildClickListener(OnChildClickListener mOnChildClickListener) {
        this.mOnChildClickListener = mOnChildClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Tab item) {
        helper.setText(R.id.treeTitle, item.getName());
        FlexboxLayout fbl = helper.getView(R.id.fbl);
        fbl.removeAllViews();
        List<Tab> children = item.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Tab childBean = children.get(i);
            TextView childView = getChildTextView(fbl);
            childView.setText(childBean.getName());
            fbl.addView(childView);
            int position = i;
            childView.setOnClickListener(v -> mOnChildClickListener.onChildClick(position, item));
        }
        helper.itemView.setOnClickListener(v -> mOnChildClickListener.onChildClick(0, item));

    }

    private TextView getChildTextView(FlexboxLayout fbl) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(fbl.getContext());
        }
        return (TextView)mLayoutInflater.inflate(R.layout.item_knowledge_child, fbl, false);
    }

    public interface OnChildClickListener {
        void onChildClick(int position, Tab data);
    }

}
