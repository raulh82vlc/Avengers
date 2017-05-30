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

package com.raulh82vlc.AvengersComics.di.modules;

import com.raulh82vlc.AvengersComics.BuildConfig;
import com.raulh82vlc.AvengersComics.domain.datasources.net.NetDataSourceImpl;
import com.raulh82vlc.AvengersComics.domain.datasources.net.NetOperations;
import com.raulh82vlc.AvengersComics.domain.datasources.net.NetOperationsImpl;
import com.raulh82vlc.AvengersComics.domain.datasources.net.connection.ConnectionHandler;
import com.raulh82vlc.AvengersComics.domain.datasources.net.connection.ConnectionHandlerImpl;
import com.raulh82vlc.AvengersComics.domain.datasources.net.parameters.ComicsAPIVariables;
import com.raulh82vlc.AvengersComics.domain.datasources.net.parameters.ComicsAPIVariablesImpl;
import com.raulh82vlc.AvengersComics.domain.repository.ComicRepositoryImpl;
import com.raulh82vlc.AvengersComics.domain.repository.ComicsRepository;
import com.raulh82vlc.AvengersComics.domain.repository.datasources.NetDataSource;
import com.raulh82vlc.AvengersComics.domain.repository.datasources.api.ComicsApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>Comics repository module which provides the different specific implementations as
 * well as the {@link NetDataSource} which retrieves info from the Network and all sub-components
 * required for starting Network communication</p>
 *
 * @author Raul Hernandez Lopez
 */

@Module
public class ComicsRepositoryModule {
    @Provides
    @Singleton
    ComicsRepository provideComicsRepository(ComicRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    NetDataSource provideNetDataSource(NetDataSourceImpl dataSource) {
        return dataSource;
    }

    @Provides
    @Singleton
    ConnectionHandler provideConnectionHandler() {
        return new ConnectionHandlerImpl();
    }

    @Provides
    @Singleton
    ComicsAPIVariables provideComicsAPIVariables() {
        return new ComicsAPIVariablesImpl();
    }

    @Provides
    public ComicsApi provideComicsApi(Retrofit retrofit) {
        return retrofit.create(ComicsApi.class);
    }

    @Provides
    @Singleton
    NetOperations provideNetOperations(ComicsApi comicsApi, ComicsAPIVariables comicsVariables, ConnectionHandler connectionHandler) {
        return new NetOperationsImpl(comicsApi, comicsVariables, connectionHandler);
    }

    @Provides
    @Singleton
    public Converter.Factory providesConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);

        return okHttpClient.build();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_API)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build();
    }
}
