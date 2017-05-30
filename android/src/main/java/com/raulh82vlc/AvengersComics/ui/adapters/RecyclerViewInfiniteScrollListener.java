/*
 * Copyright (C) 2017 Raul Hernandez Lopez @raulh82vlc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raulh82vlc.AvengersComics.ui.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * <p>Inspired in this https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
 *
 * <p>Sorry, since I am only using {@link LinearLayoutManager} I have not used other solutions like:
 * https://gist.github.com/nesquena/d09dc68ff07e845cc622</p>
 *
 * @author Raul Hernandez Lopez.
 */

public class RecyclerViewInfiniteScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 4;
    private int prevTotalItemCount = 0;
    private boolean loading = true;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager layoutManager;

    public RecyclerViewInfiniteScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        onLoadMoreListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (dy > 0) {
            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            if (totalItemCount < prevTotalItemCount) {
                prevTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }

            if (loading && (totalItemCount > prevTotalItemCount)) {
                loading = false;
                prevTotalItemCount = totalItemCount;
            }

            if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
                loading = true;
                prevTotalItemCount = totalItemCount;
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
