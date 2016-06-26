package org.sysu.sjk.gankclient.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.sysu.sjk.gankclient.R;

/**
 * 参考：https://github.com/drakeet/Meizhi/blob/master/app/src/main/java/me/drakeet/meizhi/ui/adapter/AnimRecyclerViewAdapter.java
 * 让RecyclerView中的item项有动画
 * Created by sjk on 2016/6/26.
 */
public class AnimatedRecyclerViewAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> {

    private static final int DELAY = 100;
    private int lastItemPos = -1;

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 每个item的动画
     * @param view
     * @param position
     */
    public void showAnimationForEachItem(final View view, int position) {
        if (position > lastItemPos) {
            view.setAlpha(0.0f);
            final Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1.0f);    // 如果没有这句话就不会显示了……琢磨一下
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.startAnimation(animation);
                }
            }, DELAY * position);
            lastItemPos = position;
        }
    }
}
