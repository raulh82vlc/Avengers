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

package com.raulh82vlc.AvengersComics.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Comics data response including offset, limit, total, count and finally results (comics)
 * @author Raul Hernandez Lopez.
 */

public class ComicsDataResponse {
    @SerializedName("offset")
    private int offsetPages;
    @SerializedName("limit")
    private int limitPages;
    @SerializedName("total")
    private int totalPages;
    @SerializedName("count")
    private int countComics;
    @SerializedName("results")
    private List<ComicResponse> listOfComics;

    public int getOffsetPages() {
        return offsetPages;
    }

    public int getLimitPages() {
        return limitPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCountComics() {
        return countComics;
    }

    public List<ComicResponse> getComicsList() {
        return listOfComics;
    }

    @Override
    public String toString() {
        return "ComicsDataResponse {" +
                "offsetPages= " + offsetPages +
                ", limitPages= " + limitPages +
                ", totalPages= " + totalPages +
                ", countComics= " + countComics +
                ", listOfComics= " + listOfComics +
                "}";
    }
}
