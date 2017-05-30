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
 * Comic Item response
 * @author Raul Hernandez Lopez.
 */
public class ComicResponse {
    @SerializedName("id")
    private long comicId;
    @SerializedName("digitalId")
    private long digitalComicId;
    @SerializedName("title")
    private String titleComic;
    @SerializedName("issueNumber")
    private double issueMarvelNumber;
    @SerializedName("description")
    private String descriptionComic;
    @SerializedName("pageCount")
    private int pageCountInTotal;
    @SerializedName("creators")
    private ComicCharactersResponse creatorsOfComic;
    @SerializedName("characters")
    private ComicCharactersResponse charactersOfComic;
    @SerializedName("thumbnail")
    private ComicImageResponse thumbnailImage;
    @SerializedName("images")
    private List<ComicImageResponse> imagesRandom;

    public long getComicId() {
        return comicId;
    }

    public long getDigitalComicId() {
        return digitalComicId;
    }

    public String getTitleComic() {
        return titleComic;
    }

    public double getIssueMarvelNumber() {
        return issueMarvelNumber;
    }

    public String getDescriptionComic() {
        return descriptionComic;
    }

    public int getPageCountInTotal() {
        return pageCountInTotal;
    }

    public ComicCharactersResponse getCreatorsOfComic() {
        return creatorsOfComic;
    }

    public ComicCharactersResponse getCharactersOfComic() {
        return charactersOfComic;
    }

    public ComicImageResponse getThumbnailImage() {
        return thumbnailImage;
    }

    public List<ComicImageResponse> getImagesRandom() {
        return imagesRandom;
    }

    @Override
    public String toString() {
        return "ComicResponse {"
                + "comicId= " + comicId
                + ", digitalComicId= " + digitalComicId
                + ", titleComic= " + titleComic
                + ", issueMarvelNumber= " + issueMarvelNumber
                + ", descriptionComic= " + descriptionComic
                + ", creatorsOfComic= " + creatorsOfComic
                + ", characters OfComic= " + charactersOfComic
                + "}";
    }
}
