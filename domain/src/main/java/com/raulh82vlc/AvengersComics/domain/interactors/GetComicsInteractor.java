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
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;

import java.util.Map;

/**
 * Get Comics List Use case
 *
 * @author Raul Hernandez Lopez
 */
public interface GetComicsInteractor {

    void executeLoadMore(String heroID, Map<String, String> mapOfAvenger, GetComicsListCallback callback) throws ConnectionException, HttpException;

    void execute(String heroID, Map<String, String> mapOfAvenger, GetComicsListCallback callback) throws ConnectionException, HttpException;

    interface GetComicsListCallback {
        void onGetComicsListOK(ComicsResponse comicList);

        void onGetComicsListKO(String error);
    }
}
