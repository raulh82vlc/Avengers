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

package com.raulh82vlc.AvengersComics.domain.interactors.mappers;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.raulh82vlc.AvengersComics.di.scopes.ActivityScope;
import com.raulh82vlc.AvengersComics.domain.models.ComicCharactersResponse;
import com.raulh82vlc.AvengersComics.domain.models.ComicResponse;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * As a Mapper, means it is a converter between different domains
 *
 * @author Raul Hernandez Lopez
 */
@ActivityScope
public class ComicsListModelDataMapper {

    @Inject
    ComicsListModelDataMapper() {

    }

    /**
     * Transforms a {@link ComicsResponse} into a List of {@link ComicUI}
     *
     * @param comicsList to be transformed.
     * @return List of {@link ComicUI}
     */
    public List<ComicUI> transform(ComicsResponse comicsList) {
        if (comicsList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        List<ComicResponse> comicsListResponse = comicsList.getComicsDataResponse().getComicsList();
        int size = comicsListResponse.size();
        List<ComicUI> listOfComics = new ArrayList<>(size);
        if (size > 0) {
            for (ComicResponse comic : comicsListResponse) {
                String uri = null;
                if (!comic.getImagesRandom().isEmpty()
                        && !TextUtils.isEmpty(comic.getImagesRandom().get(0).getUri())
                        && !TextUtils.isEmpty(comic.getImagesRandom().get(0).getExtensionOfImage())) {
                    uri = comic.getImagesRandom().get(0).getUri()
                        + "." + comic.getImagesRandom().get(0).getExtensionOfImage();
                }
                String creators = getPeople(comic.getCreatorsOfComic().getCreators());
                String characters = getPeople(comic.getCharactersOfComic().getCreators());
                listOfComics.add(
                        new ComicUI(
                                comic.getTitleComic(),
                                comic.getThumbnailImage().getUri(),
                                comic.getDescriptionComic(),
                                creators, characters,
                                comic.getComicId(), uri));
            }
        }
        return listOfComics;
    }

    @NonNull
    /**
     * Get a group of people in a readable format
     */
    public String getPeople(List<ComicCharactersResponse.CreatorResponse> listOfPeople) {
        StringBuilder builder = new StringBuilder();
        for (ComicCharactersResponse.CreatorResponse creator : listOfPeople) {
            String role = "";
            if (!TextUtils.isEmpty(creator.getRoleOfCreator())) {
                role = creator.getRoleOfCreator() + ": ";
            }
            builder.append(role);
            builder.append(creator.getNameOfCreator());
            builder.append("\n");
        }
        return builder.toString();
    }
}
