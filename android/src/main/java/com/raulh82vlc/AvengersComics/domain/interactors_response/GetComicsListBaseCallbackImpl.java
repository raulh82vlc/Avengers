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

package com.raulh82vlc.AvengersComics.domain.interactors_response;

import com.raulh82vlc.AvengersComics.domain.interactors.GetComicsInteractor;
import com.raulh82vlc.AvengersComics.domain.interactors.mappers.ComicsListModelDataMapper;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;
import com.raulh82vlc.AvengersComics.ui.presentation.ComicsPresenter;

import java.util.List;

/**
 * Get Comics list by means of its callback, communicating towards its view, mapping resuts from the API
 * to the view
 * @author Raul Hernandez Lopez.
 */

public abstract class GetComicsListBaseCallbackImpl implements GetComicsInteractor.GetComicsListCallback {
    private final ComicsPresenter.View view;
    private final ComicsListModelDataMapper comicsListModelDataMapper;

    /**
     * This abstract method is implemented by its children depending if needed onLoadMore or a normal load
     * @param comics collection of comics
     */
    abstract void addComics(ComicsPresenter.View view, List<ComicUI> comics);

    public GetComicsListBaseCallbackImpl(ComicsPresenter.View view,
                                         ComicsListModelDataMapper comicsListModelDataMapper) {
        this.view = view;
        this.comicsListModelDataMapper = comicsListModelDataMapper;
    }

    @Override
    public void onGetComicsListOK(ComicsResponse comicList) {
        if (view.isReady()) {
            List<ComicUI> comicsList = comicsListModelDataMapper.transform(comicList);
            view.hideLoader();
            if (comicsList.isEmpty()) {
                view.hideListShowText();
                view.showEmptyState();
            } else {
                view.showList();
                addComics(view, comicsList);
                view.showAnimation();
            }
        }
    }

    @Override
    public void onGetComicsListKO(String error) {
        if (view.isReady()) {
            view.hideLoader();
            view.showEmptyState();
            view.errorGettingComics(error);
        }
    }
}
