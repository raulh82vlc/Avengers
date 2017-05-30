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

package com.raulh82vlc.AvengersComics.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh82vlc.AvengersComics.R;
import com.raulh82vlc.AvengersComics.domain.exceptions.ConnectionException;
import com.raulh82vlc.AvengersComics.domain.exceptions.HttpException;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;
import com.raulh82vlc.AvengersComics.ui.activities.ComicDetailsActivity;
import com.raulh82vlc.AvengersComics.ui.activities.ComicsListActivity;
import com.raulh82vlc.AvengersComics.ui.adapters.ComicsListAdapter;
import com.raulh82vlc.AvengersComics.ui.adapters.RecyclerViewInfiniteScrollListener;
import com.raulh82vlc.AvengersComics.ui.presentation.ComicsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import timber.log.Timber;

/**
 * <p>Comics List Fragment which uses all injected views or components</p>
 * <p>first of all when the activity is created the component,
 * as well as presenter and view injections for each UI element</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ComicsListFragment extends BaseFragment implements
        ComicsPresenter.View,
        ComicsListAdapter.OnItemClickListener,
        RecyclerViewInfiniteScrollListener.OnLoadMoreListener {

    /**
     * UI injections
     */
    @InjectView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @InjectView(R.id.no_results_view)
    public TextView noResultsTextView;
    /**
     * DI
     */
    @Inject
    ComicsPresenter comicsPresenter;

    // UI Widgets
    private ComicsListAdapter adapter;
    private ComicsListActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comics_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ComicsListActivity) context;
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.component().inject(this);
        comicsPresenter.setView(this);
        comicsPresenter.initInitialVisibility();
        loadAvenger(getString(R.string.captain_america_id));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
    }

    @Override
    public void onDestroyView() {
        comicsPresenter.resetView();
        adapter = null;
        super.onDestroyView();
    }

    /**
     * <p>Sets the adapter and recyclerview</p>
     **/
    private void setRecyclerView() {
        adapter = new ComicsListAdapter();
        adapter.setOnItemClickFromList(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        setInfiniteScroll(linearLayoutManager);
    }

    private void setInfiniteScroll(LinearLayoutManager linearLayoutManager) {
        RecyclerViewInfiniteScrollListener loadMoreListener =
                new RecyclerViewInfiniteScrollListener(linearLayoutManager);
        loadMoreListener.setOnLoadMoreListener(this);
        recyclerView.addOnScrollListener(loadMoreListener);
    }

    private void setUIWidgetsVisibility(int visible, int gone) {
        noResultsTextView.setVisibility(visible);
        recyclerView.setVisibility(gone);
    }

    private void loadAvenger(String heroID) {
        setDictionary(heroID);
        try {
            comicsPresenter.getComics(heroID);
        } catch (ConnectionException | HttpException e) {
            comicsPresenter.raiseException(e.getMessage());
        }
    }

    private void setDictionary(String heroID) {
        comicsPresenter.addAvenger(heroID, getString(R.string.avenger_1));
    }

    @Override
    public void showInitialVisibility() {
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
    }

    @Override
    public void errorGettingComics(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemFromListClick(ComicUI comicUI, View view) {
        /** go to the detail screen */
        ComicDetailsActivity.navigateToDetailsActivity(activity, comicUI, view.findViewById(R.id.iv_image));
    }

    @Override
    public void updateComicsList(List<ComicUI> comicsList) {
        adapter.updateComics(comicsList);
    }

    @Override
    public void addMoreComicsToList(List<ComicUI> comicsList) {
        adapter.addMoreComics(comicsList);
    }

    @Override
    public void showList() {
        setUIWidgetsVisibility(View.GONE, View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        activity.hideLoader();
    }

    @Override
    public void showAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void showLoader(String avengerName) {
        activity.showLoaderWithTitleAndDescription(avengerName, getString(R.string.loading));
    }

    @Override
    public void showEmptyState() {
        noResultsTextView.setText(getString(R.string.no_results));
    }

    @Override
    public void showErrorMessage(String error) {
        Timber.e(error);
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void hideListShowText() {
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
    }

    @Override
    public void onLoadMore() {
        setDictionary(getString(R.string.captain_america_id));
        try {
            comicsPresenter.loadMoreComics();
        } catch (ConnectionException | HttpException e) {
            comicsPresenter.raiseException(e.getMessage());
        }
    }
}
