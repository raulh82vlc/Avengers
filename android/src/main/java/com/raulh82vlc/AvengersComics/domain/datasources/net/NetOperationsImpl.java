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
import android.util.Log;

import com.raulh82vlc.AvengersComics.BuildConfig;
import com.raulh82vlc.AvengersComics.R;
import com.raulh82vlc.AvengersComics.domain.datasources.net.connection.ConnectionHandler;
import com.raulh82vlc.AvengersComics.domain.datasources.net.parameters.ComicsAPIVariables;
import com.raulh82vlc.AvengersComics.domain.exceptions.ConnectionException;
import com.raulh82vlc.AvengersComics.domain.exceptions.HttpException;
import com.raulh82vlc.AvengersComics.domain.exceptions.IOCustomException;
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;
import com.raulh82vlc.AvengersComics.domain.repository.datasources.api.ComicsApi;

import java.io.IOException;
import java.text.MessageFormat;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Declared read operations from the Network API interface
 *
 * @author Raul Hernandez Lopez
 */
public class NetOperationsImpl implements NetOperations<ComicsResponse> {

    private static final String TAG = NetOperationsImpl.class.getSimpleName();
    private final ComicsApi comicsApi;
    private final ComicsAPIVariables parameters;
    private final ConnectionHandler connectionHandler;

    @Inject
    public NetOperationsImpl(ComicsApi comicsApi, ComicsAPIVariables parameters, ConnectionHandler connectionHandler) {
        this.comicsApi = comicsApi;
        this.parameters = parameters;
        this.connectionHandler = connectionHandler;
    }

    @Override
    public ComicsResponse getComicsList(Context context, String heroID, int pageId)
            throws ConnectionException, HttpException {
        if (connectionHandler.isThereConnection(context)) {
            Call<ComicsResponse> comicsResponseCall = comicsApi.getComicsFromHero(heroID,
                    parameters.getTimeStamp(), parameters.getPublicAPIKey(), parameters.getAPIHashCode(),
                    parameters.getPageLimit(), getPageOffset(pageId));
            try {
                Response<ComicsResponse> response = comicsResponseCall.execute();
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, MessageFormat.format("Http header: {0}", response.headers().toString()));
                    Log.d(TAG, MessageFormat.format("Http response: {0}", response.message()));
                }
                if (isSuccess(response.code())) {
                    ComicsResponse comicsResponse = response.body();
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, MessageFormat.format("Http response code: {0}", comicsResponse.getRequestCode()));
                        Log.d(TAG, MessageFormat.format("Http response body: {0}", comicsResponse.toString()));
                    }
                    return comicsResponse;
                } else {
                    Log.e(TAG, MessageFormat.format("Error: Http response code: {0}", response.code()));
                    throw new HttpException(context.getResources().getString(R.string.error_http, response.code()));
                }
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
                throw new IOCustomException(context.getResources().getString(R.string.error_io, e.getMessage()));
            }
        } else {
            throw new ConnectionException(context.getResources().getString(R.string.no_connection));
        }
    }

    /**
     * Depending on the page number there is a different offset, i.e. page 2 = 2 * 30 = 60 is the offset
     * @param pageId page where we are
     * @return offset
     */
    private int getPageOffset(int pageId) {
        return pageId * parameters.getPageLimit();
    }

    @Override
    public boolean isSuccess(int code) {
        return code == parameters.getValidCode();
    }
}
