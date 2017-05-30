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

package com.raulh82vlc.AvengersComics.domain.repository;

import com.raulh82vlc.AvengersComics.domain.datasources.net.NetDataSourceImpl;
import com.raulh82vlc.AvengersComics.domain.exceptions.ConnectionException;
import com.raulh82vlc.AvengersComics.domain.exceptions.HttpException;
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;
import com.raulh82vlc.AvengersComics.domain.repository.datasources.NetDataSource;

import javax.inject.Inject;


/**
 * <p>Implements {@link ComicsRepository} and allows to have one or more Data source
 * like {@link NetDataSource} or one another on it</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ComicRepositoryImpl implements ComicsRepository<ComicsResponse> {

    private NetDataSource<ComicsResponse> netDataSource;
    private int pageID = 0;

    @Inject
    ComicRepositoryImpl(NetDataSourceImpl netDataSource) {
        this.netDataSource = netDataSource;
    }

    @Override
    public ComicsResponse getComicsListByPage(String heroID) throws ConnectionException, HttpException {
        pageID++;
        return netDataSource.getComicsList(heroID, pageID);
    }

    @Override
    public ComicsResponse getComicsList(String heroID) throws ConnectionException, HttpException {
        pageID = 0;
        return netDataSource.getComicsList(heroID, pageID);
    }
}
