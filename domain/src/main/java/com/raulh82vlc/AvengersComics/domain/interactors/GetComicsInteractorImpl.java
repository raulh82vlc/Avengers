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

package com.raulh82vlc.AvengersComics.domain.interactors;

import com.raulh82vlc.AvengersComics.domain.exceptions.ConnectionException;
import com.raulh82vlc.AvengersComics.domain.exceptions.HttpException;
import com.raulh82vlc.AvengersComics.domain.executors.Interactor;
import com.raulh82vlc.AvengersComics.domain.executors.InteractorExecutor;
import com.raulh82vlc.AvengersComics.domain.executors.MainThread;
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;
import com.raulh82vlc.AvengersComics.domain.repository.ComicsRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation of the Get Comics List Interactor
 *
 * @author Raul Hernandez Lopez
 */
public class GetComicsInteractorImpl implements GetComicsInteractor, Interactor {

    final private InteractorExecutor executor;
    final private MainThread mainThread;
    final private ComicsRepository<ComicsResponse> repository;
    private GetComicsListCallback callback;
    private boolean isPaged;
    private String heroID;
    private Map<String, String> mapOfAvenger = new HashMap<>();

    @Inject
    GetComicsInteractorImpl(InteractorExecutor executor,
                            MainThread mainThread,
                            ComicsRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void executeLoadMore(String heroID, Map<String, String> mapOfAvenger,
                                GetComicsListCallback callback) throws ConnectionException, HttpException {
        isPaged = true;
        setVariables(heroID, mapOfAvenger, callback);
        executor.run(this);
    }

    @Override
    public void execute(String heroID, Map<String, String> mapOfAvenger,
                        GetComicsListCallback callback) throws ConnectionException, HttpException {
        isPaged = false;
        setVariables(heroID, mapOfAvenger, callback);
        executor.run(this);
    }

    private void setVariables(String heroID, Map<String, String> mapOfAvenger, GetComicsListCallback callback) {
        this.heroID = heroID;
        this.callback = callback;
        mapOfAvenger.clear();
        mapOfAvenger.putAll(mapOfAvenger);
    }

    @Override
    public void run() throws ConnectionException, HttpException {
        String errorMessage = "Issue when reading comic from hero: ";
        try {
            ComicsResponse comicsList;
            comicsList = getComicsResponse();
            if (comicsList != null) {
                notifySuccessfullyLoaded(comicsList);
            } else {
                notifyError(errorMessage + mapOfAvenger.get(heroID));
            }
        } catch (ConnectionException | HttpException e) {
            notifyError(e.getMessage());
        }
    }

    private ComicsResponse getComicsResponse() throws ConnectionException, HttpException {
        ComicsResponse comicsList;
        if (isPaged) {
            comicsList = repository.getComicsListByPage(heroID);
        } else {
            comicsList = repository.getComicsList(heroID);
        }
        return comicsList;
    }

    /**
     * <p>Notifies to the UI (main) thread the result of the request,
     * and sends a callback with a list</p>
     */
    private void notifySuccessfullyLoaded(final ComicsResponse comicList) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetComicsListOK(comicList);
            }
        });
    }

    /**
     * <p>Notifies to the UI (main) thread that an error happened</p>
     */
    private void notifyError(final String error) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetComicsListKO(error);
            }
        });
    }
}
