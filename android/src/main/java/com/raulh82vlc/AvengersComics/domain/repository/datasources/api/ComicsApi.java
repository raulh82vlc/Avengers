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

package com.raulh82vlc.AvengersComics.domain.repository.datasources.api;

import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Comics Api interface for Avengers used on Retrofit
 * @author Raul Hernandez Lopez.
 */

public interface ComicsApi {

    @GET("characters/{characterId}/comics")
    Call<ComicsResponse> getComicsFromHero(@Path("characterId") String heroId,
                                           @Query("ts") String timeStamp,
                                           @Query("apikey") String apiPublicKey,
                                           @Query("hash") String hashCode,
                                           @Query("limit") int limitPages,
                                           @Query("offset") int pageOffset);

}
