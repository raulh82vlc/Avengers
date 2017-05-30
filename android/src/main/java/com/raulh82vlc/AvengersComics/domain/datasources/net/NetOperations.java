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

package com.raulh82vlc.AvengersComics.domain.datasources.net;

import android.content.Context;

import com.raulh82vlc.AvengersComics.domain.exceptions.ConnectionException;
import com.raulh82vlc.AvengersComics.domain.exceptions.HttpException;

/**
 * Network Operations API contract
 *
 * @author Raul Hernandez Lopez
 */
public interface NetOperations<C> {

    /**
     * Gets a the list of comics from the Network
     *
     * @param heroID hero identifier
     * @param pageID page of the comics request, since it is paged*/
    C getComicsList(Context context, String heroID, int pageID) throws ConnectionException, HttpException;

    boolean isSuccess(int code);
}
