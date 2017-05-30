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

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raulh82vlc.AvengersComics.R;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;
import com.raulh82vlc.AvengersComics.ui.viewholders.ComicViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Comics listing
 */
public class ComicsListAdapter extends RecyclerView.Adapter<ComicViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<ComicUI> comicsUIs = new ArrayList<>();

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            if (onItemClickListener != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onItemClickListener.onItemFromListClick((ComicUI) view.getTag(), view);
                    }
                }, 200);
            }
        }
    };

    public ComicsListAdapter() {
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemListView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list, parent, false);
        ComicViewHolder comicViewHolder = new ComicViewHolder(itemListView);
        comicViewHolder.onSetClickListener(onClickListener);
        return comicViewHolder;
    }

    @Override
    public void onBindViewHolder(final ComicViewHolder comicComicViewHolder, int position) {
        comicComicViewHolder.onBindView(comicsUIs.get(position));
    }

    @Override
    public int getItemCount() {
        return comicsUIs.size();
    }

    public void setOnItemClickFromList(OnItemClickListener onItemClickFromList) {
        onItemClickListener = onItemClickFromList;
    }

    public interface OnItemClickListener {
        void onItemFromListClick(ComicUI productUI, View view);
    }

    public void updateComics(List<ComicUI> comicsUIList) {
        comicsUIs.clear();
        comicsUIs.addAll(comicsUIList);
        notifyDataSetChanged();
    }

    public void addMoreComics(List<ComicUI> comicsUIList) {
        for (ComicUI comic : comicsUIList) {
            comicsUIs.add(comic);
            notifyItemInserted(getItemCount() - 1);
        }
    }
}
