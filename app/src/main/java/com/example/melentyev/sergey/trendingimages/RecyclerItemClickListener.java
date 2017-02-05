package com.example.melentyev.sergey.trendingimages;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat gestureDetector;

    interface OnRecyclerClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public RecyclerItemClickListener(
            Context context, final RecyclerView recyclerView, OnRecyclerClickListener mListener) {
        this.mListener = mListener;
        this.gestureDetector = null;

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return super.onInterceptTouchEvent(rv, e);
    }
}
