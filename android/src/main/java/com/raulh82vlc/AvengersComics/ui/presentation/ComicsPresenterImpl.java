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

package com.raulh82vlc.AvengersComics.ui.presentation;

import com.raulh82vlc.AvengersComics.domain.exceptions.ConnectionException;
import com.raulh82vlc.AvengersComics.domain.exceptions.HttpException;
import com.raulh82vlc.AvengersComics.domain.interactors.GetComicsInteractor;
import com.raulh82vlc.AvengersComics.domain.interactors.mappers.ComicsListModelDataMapper;
import com.raulh82vlc.AvengersComics.domain.interactors_response.GetComicsListCallbackImpl;
import com.raulh82vlc.AvengersComics.domain.interactors_response.GetMoreComicsListCallbackImpl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation of the {@link ComicsPresenter}
 *
 * @author Raul Hernandez Lopez
 */
public class ComicsPresenterImpl implements ComicsPresenter {

    private final GetComicsInteractor interactorGetComicsList;
    private final ComicsListModelDataMapper comicsListModelDataMapper;
    private View view;

    final private Map<String, String> mapOfAvenger = new HashMap<>();
    private String heroID = null;

    @Inject
    ComicsPresenterImpl(
            GetComicsInteractor interactorGetComicsList,
            ComicsListModelDataMapper comicsListModelDataMapper) {
        this.interactorGetComicsList = interactorGetComicsList;
        this.comicsListModelDataMapper = comicsListModelDataMapper;
    }

    @Override
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The view should be instantiated");
        }
        this.view = view;
    }

    @Override
    public void resetView() {
        view = null;
    }

    @Override
    public void raiseException(String message) {
        if (view.isReady()) {
            view.showErrorMessage(message);
        }
    }

    @Override
    public void initInitialVisibility() {
        if (view.isReady()) {
            view.showInitialVisibility();
        }
    }

    @Override
    public void addAvenger(String heroID, String heroName) {
        mapOfAvenger.put(heroID, heroName);
    }

    @Override
    public void loadMoreComics() throws ConnectionException, HttpException {
        if (view.isReady()) {
            if (mapOfAvenger.containsKey(heroID)) {
                view.showLoader(mapOfAvenger.get(heroID));
                interactorGetComicsList.executeLoadMore(heroID, mapOfAvenger, new GetMoreComicsListCallbackImpl(view, comicsListModelDataMapper));
            } else {
                errorCase("No hero recognised called " + mapOfAvenger.get(heroID));
            }
        }
    }

    @Override
    public void getComics(String heroID) throws ConnectionException, HttpException {
        if (view.isReady()) {
            if (mapOfAvenger.containsKey(heroID)) {
                this.heroID = heroID;
                view.showLoader(mapOfAvenger.get(heroID));
                interactorGetComicsList.execute(heroID, mapOfAvenger, new GetComicsListCallbackImpl(view, comicsListModelDataMapper));
            } else {
                errorCase("No hero recognised called " + mapOfAvenger.get(heroID));
            }
        }
    }

    private void errorCase(String error) {
        view.hideLoader();
        view.showEmptyState();
        view.errorGettingComics(error);
    }
}
