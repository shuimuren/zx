package com.zhixing.work.zhixin.base;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.ListRecycleViewAdapter;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhj on 2018/8/3.
 * Description:
 */

public abstract class BaseListActivity<T> extends BaseTitleActivity implements ListRecycleViewAdapter.Callback<T> {

    protected static final int PAGE_START = 0;
    protected static final int PAGE_SIZE = 10;
    protected LinearLayoutManager mLayoutManager;
    protected ListRecycleViewAdapter mListAdapter;
    protected int mPages = PAGE_START;
    protected boolean mIsLoadingMore = false;
    protected int mLastVisibleItem;
    protected int mPageCount = -1;
    protected List<T> mData = new ArrayList<>();


    protected RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDecoration();
        setContentViewLayout();
        mListView = findViewById(R.id.recycler_list);
        initContent();
    }

    private void initContent() {
        initListLayout();
        initView();
        getListSafe();
    }

    protected void initListLayout() {
        mLayoutManager = new LinearLayoutManager(this);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(mLayoutManager);
        mListAdapter = new ListRecycleViewAdapter(this, mData, this);
        mListView.setAdapter(mListAdapter);
        mListView.setItemAnimator(new DefaultItemAnimator());
        if (addDecoration()) {
            mListView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));
        }

        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 1 == mListAdapter.getItemCount()) {
                    loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });


    }

    private void loadMore() {
        if (getListSafe()) {
            mIsLoadingMore = true;
        }
    }

    private boolean getListSafe() {
        if (mPageCount < 0 || mPages + 1 <= mPageCount) {
            mPages++;
            dispatchRequest();
            return true;
        }
        return false;
    }

    protected void onGetListSucceeded(int pageCount, List<T> list) {
        mPageCount = pageCount;
        if (list != null) {
            if (!mIsLoadingMore) {
                mData.clear();
            }
            mData.addAll(list);
            mListAdapter.setIsNoMore(mPages == mPageCount);
            mListAdapter.setData(mData);
        }
    }

    protected void onGetListFailed(String errorMsg) {

        AlertUtils.show(errorMsg);
    }

    protected abstract boolean addDecoration();

    protected abstract void setContentViewLayout();

    protected abstract void dispatchRequest();

    protected abstract void initView();

    @Override
    public void onItemClicked(T bean) {

    }

    @Override
    public boolean isPaged() {
        return true;
    }

    @Override
    public void onLoadMoreButtonClicked() {
        loadMore();
    }
}
