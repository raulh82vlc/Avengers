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
 * Comic Creators or Characters Response (since the structure is the same, I have reused it)
 *
 * @author Raul Hernandez Lopez.
 */

public class ComicCharactersResponse {
    @SerializedName("available")
    private int isAvailaible;
    @SerializedName("returned")
    private int isReturned;
    @SerializedName("collectionURI")
    private String uri;
    @SerializedName("items")
    private List<CreatorResponse> creators;

    public int getIsAvailaible() {
        return isAvailaible;
    }

    public int getIsReturned() {
        return isReturned;
    }

    public String getUri() {
        return uri;
    }

    public List<CreatorResponse> getCreators() {
        return creators;
    }

    @Override
    public String toString() {
        return "ComicCreatorsOrCharactersResponse {"
                + "isAvailaible= "
                + isAvailaible + '\''
                + ", isReturned= " + isReturned
                + '\''
                + ", uri= " + uri
                + '\'' + ", creators= " + creators
                + '\''
                + '}' + "\n";
    }

    /**
     * Comic Creator Response inner class
     *
     * @author Raul Hernandez Lopez.
     */
    public static class CreatorResponse {
        @SerializedName("name")
        private String nameOfCreator;
        @SerializedName("resourceURI")
        private String uri;
        @SerializedName("role")
        private String roleOfCreator;

        public String getUri() {
            return uri;
        }

        public String getNameOfCreator() {
            return nameOfCreator;
        }

        public String getRoleOfCreator() {
            return roleOfCreator;
        }

        @Override
        public String toString() {
            return "CreatorResponse {"
                    + "resourceURI= '" + uri
                    + '\'' + ", name= '" + nameOfCreator + '\''
                    + ", role= '" + roleOfCreator
                    + '\''
                    + '}';
        }
    }
}
