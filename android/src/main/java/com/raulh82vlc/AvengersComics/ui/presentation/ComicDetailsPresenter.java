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

package com.raulh82vlc.AvengersComics.ui.presentation;

import android.content.Context;

import com.raulh82vlc.AvengersComics.domain.models.ComicUI;

/**
 * Responsible of passing Comics List result to the View
 *
 * @author Raul Hernandez Lopez
 */
public interface ComicDetailsPresenter {

    void setView(View view);

    void resetView();

    void setInfo(ComicUI comic, Context context);

    void errorGettingInfo(Context context);

    void finishDueToError();

    interface View {

        void errorWithComicDetail(String error);

        boolean isReady();

        void finishAfterError();

        void showComicTitle(String comicTitle);

        void showDescription(String description);

        void showCreators(String creators);

        void showCharacters(String characters);

        void showMainImage(String uriRandom);

        void showComicId(String comicId);

        void showEffect();
    }
}
