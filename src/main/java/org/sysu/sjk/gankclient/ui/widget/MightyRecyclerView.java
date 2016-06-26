package org.sysu.sjk.gankclient.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.adapter.AnimatedRecyclerViewAdapter;
import org.sysu.sjk.gankclient.bean.Gank;
import org.sysu.sjk.gankclient.ui.activity.GankDetailActivity;
import org.sysu.sjk.gankclient.utils.SpannableUtils;
import org.w3c.dom.Text;

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
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
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
    }

    /**
     * 建立List的引用联系，该引用一直不变，为了让notifyDatasetChanged()一直有效
     *
     * @param ref
     */
    public void setListRef(List<Gank> ref) {
        gankList = ref;
        adapter = new GankListAdapter(mContext, gankList);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 关闭刷新的进度条
     */
    public void closeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 一开始的刷新
     */
    public void autoRefreshInTheBeginning() {
        if (mOnRefreshListener == null) {
            throw new IllegalArgumentException("刷新监听器\"OnRefreshListener\"未定义!");
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mOnRefreshListener.onRefresh();
            }
        });
    }

    /**
     * 设置刷新进度条颜色
     *
     * @param colorIds
     */
    public void setRefreshingColors(int... colorIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorIds);
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
        mOnRefreshListener = listener;
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
    public static class GankListAdapter extends AnimatedRecyclerViewAdapter<RecyclerView.ViewHolder> {

        public static final int ITEM_TYPE_NORMAL = 0;
        public static final int ITEM_TYPE_EXTEND = 1;

        private String lastDate = "";
        private int lastPos = -1;

        LayoutInflater layoutInflater;
        List<Gank> gankList;
        OnItemClickListener onItemClickListener;

        public GankListAdapter(Context context, List<Gank> inList) {
            this.gankList = inList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        // 截取createdAt字段的日期部分
        private String getDateStr(Gank gank) {
            //return gank.getCreatedAt().substring(0, 10);
            return gank.getPublishedAt().substring(0, 10);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder ret;
            if (viewType == ITEM_TYPE_EXTEND) {
                View view = layoutInflater.inflate(R.layout.gank_list_item_extend, parent, false);
                ret = new GankListExtendViewHolder(view);
            } else {
                View view = layoutInflater.inflate(R.layout.gank_list_item, parent, false);
                ret = new GankListNormalViewHolder(view);
            }
            return ret;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Gank gank = gankList.get(position);
            if (holder instanceof GankListExtendViewHolder) {
                GankListExtendViewHolder extendViewHolder = (GankListExtendViewHolder) holder;
                // 多了一个header
                extendViewHolder.itemExtend.setText(getDateStr(gank));
                extendViewHolder.itemPrimary.setText(SpannableUtils.getGankStr(gank));
                if (onItemClickListener != null) {
                    extendViewHolder.itemContainer.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    });
                }
                showAnimationForEachItem(extendViewHolder.itemPrimary, position);
            } else {
                GankListNormalViewHolder normalViewHolder = (GankListNormalViewHolder) holder;
                normalViewHolder.itemPrimary.setText(SpannableUtils.getGankStr(gank));
                if (onItemClickListener != null) {
                    normalViewHolder.itemContainer.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    });
                }
                showAnimationForEachItem(normalViewHolder.itemPrimary, position);
            }
        }

        @Override
        public int getItemCount() {
            return gankList.size();
        }

        /**
         * 根据日期来决定，要不要显示日期
         *
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(final int position) {
            String gankDate = getDateStr(gankList.get(position));
            if (position > lastPos && !TextUtils.equals(gankDate, lastDate)) {
                /**
                 * 改变
                 */
                lastPos = position;
                lastDate = gankDate;
                return ITEM_TYPE_EXTEND;
            } else {
                return ITEM_TYPE_NORMAL;
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            onItemClickListener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        // 没有header的
        public static class GankListNormalViewHolder extends RecyclerView.ViewHolder {
            LinearLayout itemContainer;
            TextView itemPrimary;

            public GankListNormalViewHolder(View itemView) {
                super(itemView);
                itemContainer = (LinearLayout) itemView.findViewById(R.id.item_container);
                itemPrimary = (TextView) itemView.findViewById(R.id.item_primary);
            }
        }

        // 有header的
        public static class GankListExtendViewHolder extends GankListNormalViewHolder {
            TextView itemExtend;

            public GankListExtendViewHolder(View itemView) {
                super(itemView);
                itemExtend = (TextView) itemView.findViewById(R.id.item_extend);
            }
        }
    }
}
