package org.sysu.sjk.gankclient.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.bean.Gank;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sjk on 2016/6/25.
 */
public class MightyRecyclerView extends LinearLayout {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager llm;    // 方法findLastVisibleItemPosition()
    public SwipeRefreshLayout mSwipeRefreshLayout;  // public
    private List<Gank> gankList;
    private GankListAdapter adapter;

    private int lastItemPos = 0;

    public MightyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View parentView = LayoutInflater.from(context).inflate(R.layout.mighty_recycler_view, this);

        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.custom_rv);
        llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mSwipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.custom_srl);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.darkPrimaryColor);
    }

    public void setListRef(List<Gank> ref) {
        gankList = ref;
        adapter = new GankListAdapter(mContext, gankList);
        mRecyclerView.setAdapter(adapter);
    }

    public void closeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void setOnItemClickListener(GankListAdapter.OnItemClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }

    public void setOnLoadMoreListener(final OnLoadMoreListener listener) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPos + 1 == gankList.size()) {
                    listener.onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItemPos = llm.findLastVisibleItemPosition();
            }
        });
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void refreshItems(List<Gank> newList) {
        gankList.clear();
        gankList.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    public void loadMoreItems(List<Gank> moreList) {
        int start = gankList.size();
        int count = moreList.size();
        gankList.addAll(moreList);
        adapter.notifyItemRangeInserted(start, count);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    /**
     * RecyclerView的适配器
     */
    public static class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.GankListViewHolder> {

        Context context;
        List<Gank> gankList;
        OnItemClickListener onItemClickListener;

        public GankListAdapter(Context context, List<Gank> inList) {
            this.context = context;
            this.gankList = inList;
        }

        @Override
        public GankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.gank_list_item, parent, false);
            GankListViewHolder ret = new GankListViewHolder(view);
            return ret;
        }

        @Override
        public void onBindViewHolder(GankListViewHolder holder, final int position) {
            Gank gank = gankList.get(position);
            holder.itemTitle.setText(gank.getDesc());
            holder.itemAuthor.setText(gank.getWho());

            if (null != onItemClickListener) {
                holder.itemContainer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return gankList.size();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            onItemClickListener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        // item承载器
        public static class GankListViewHolder extends RecyclerView.ViewHolder {

            LinearLayout itemContainer;
            TextView itemTitle;
            TextView itemAuthor;
            TextView itemDate;

            public GankListViewHolder(View itemView) {
                super(itemView);
                itemContainer = (LinearLayout) itemView.findViewById(R.id.item_container);
                itemTitle = (TextView) itemView.findViewById(R.id.item_title);
                itemAuthor = (TextView) itemView.findViewById(R.id.item_author);
            }
        }
    }
}
