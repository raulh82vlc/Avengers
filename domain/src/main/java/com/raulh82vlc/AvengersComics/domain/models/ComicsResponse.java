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

/**
 * Comics response as it comes from the API
 *
 * @author Raul Hernandez Lopez.
 */
public class ComicsResponse {
    @SerializedName("code")
    private String requestCode;
    @SerializedName("status")
    private String requestStatus;
    @SerializedName("copyright")
    private String marvelCopyright;
    @SerializedName("attributionText")
    private String attributionWithCopyText;
    @SerializedName("attributionHTML")
    private String attributionOnHTML;
    @SerializedName("etag")
    private String tagOrHashCode;
    @SerializedName("data")
    private ComicsDataResponse comicsDataResponse;

    public String getRequestCode() {
        return requestCode;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getMarvelCopyright() {
        return marvelCopyright;
    }

    public String getAttributionWithCopyText() {
        return attributionWithCopyText;
    }

    public String getAttributionOnHTML() {
        return attributionOnHTML;
    }

    public String getTagOrHashCode() {
        return tagOrHashCode;
    }

    public ComicsDataResponse getComicsDataResponse() {
        return comicsDataResponse;
    }

    @Override
    public String toString() {
        return "ComicsResponse {" +
                "requestCode= '" + requestCode + '\'' +
                ", requestStatus= '" + requestStatus + '\'' +
                ", marvelCopyright= '" + marvelCopyright + '\'' +
                ", attributionWithCopyText= '" + attributionWithCopyText + '\'' +
                ", attributionOnHTML= '" + attributionOnHTML + '\'' +
                ", tagOrHashCode= '" + tagOrHashCode + '\'' +
                ", comicsDataResponse= " + comicsDataResponse +
                '}';
    }
}
