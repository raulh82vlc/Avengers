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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Comic adapted to UI needs
 *
 * @author Raul Hernandez Lopez.
 */

public class ComicUI implements Parcelable {

    private final String title;
    private final String uriThumbnail;
    private final String creators;
    private final String description;
    private final long comicId;
    private final String uriRandom;
    private final String characters;

    public ComicUI(String titleComic, String uriThumbnail, String description, String creators, String characters, long comicId, String uriRandom) {
        this.title = titleComic;
        this.uriThumbnail = uriThumbnail;
        this.description = description;
        this.creators = creators;
        this.characters = characters;
        this.comicId = comicId;
        this.uriRandom = uriRandom;
    }

    private ComicUI(Parcel in) {
        title = in.readString();
        uriThumbnail = in.readString();
        description = in.readString();
        creators = in.readString();
        characters = in.readString();
        comicId = in.readLong();
        uriRandom = in.readString();
    }

    public static final Creator<ComicUI> CREATOR = new Creator<ComicUI>() {
        @Override
        public ComicUI createFromParcel(Parcel in) {
            return new ComicUI(in);
        }

        @Override
        public ComicUI[] newArray(int size) {
            return new ComicUI[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(uriThumbnail);
        dest.writeString(description);
        dest.writeString(creators);
        dest.writeString(characters);
        dest.writeLong(comicId);
        dest.writeString(uriRandom);
    }

    public String getTitle() {
        return title;
    }

    public String getUriThumbnail() {
        return uriThumbnail;
    }

    public String getCreators() {
        return creators;
    }

    public String getCharacters() {
        return characters;
    }

    public String getDescription() {
        return description;
    }

    public long getComicId() {
        return comicId;
    }

    public String getUriRandom() {
        return uriRandom;
    }
}
